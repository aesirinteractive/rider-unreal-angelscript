package com.aesirinteractive.angelscript

import com.aesirinteractive.angelscript.psi.AngelscriptClassDecl
import com.aesirinteractive.angelscript.psi.AngelscriptCompoundStatement
import com.aesirinteractive.angelscript.psi.AngelscriptConstructorDecl
import com.aesirinteractive.angelscript.psi.AngelscriptDelegateDecl
import com.aesirinteractive.angelscript.psi.AngelscriptEventDecl
import com.aesirinteractive.angelscript.psi.AngelscriptFunctionDecl
import com.aesirinteractive.angelscript.psi.AngelscriptInterfaceDecl
import com.aesirinteractive.angelscript.psi.AngelscriptMixinDecl
import com.aesirinteractive.angelscript.psi.AngelscriptNamespaceDecl
import com.aesirinteractive.angelscript.psi.AngelscriptScopeRef
import com.aesirinteractive.angelscript.psi.AngelscriptStructDecl
import com.aesirinteractive.angelscript.psi.AngelscriptTypeRef
import com.aesirinteractive.angelscript.psi.AngelscriptVariableAccessExpr
import com.aesirinteractive.angelscript.psi.AngelscriptVariableDecl
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.SmartPointerManager
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ForkJoinPool


class AngelscriptTokenType(debugName: String) : IElementType(debugName, AngelscriptLanguage.INSTANCE) {
    override fun toString(): String = "AngelscriptTokenType." + super.toString()
}

class AngelscriptElementType(debugName: String) : IElementType(debugName, AngelscriptLanguage.INSTANCE)

class AngelscriptLexerAdapter : FlexAdapter(AngelscriptLexer(null))

class AngelscriptFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, AngelscriptLanguage.INSTANCE) {
    override fun getFileType(): FileType = AngelscriptFileType.INSTANCE
    override fun toString(): String = "Angelscript File"
}

interface AngelscriptTokenSets {
    companion object {
        val IDENTIFIERS: TokenSet = TokenSet.create(AngelscriptTypes.IDENTIFIER)
        val COMMENTS: TokenSet = TokenSet.create(AngelscriptTypes.COMMENT)
    }
}

internal class AngelscriptParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = AngelscriptLexerAdapter()
    override fun getCommentTokens(): TokenSet = AngelscriptTokenSets.COMMENTS
    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY
    override fun createParser(project: Project?): PsiParser = AngelscriptParser()
    override fun getFileNodeType(): IFileElementType = FILE
    override fun createFile(viewProvider: FileViewProvider): PsiFile = AngelscriptFile(viewProvider)
    public override fun createElement(node: ASTNode?): PsiElement = AngelscriptTypes.Factory.createElement(node)

    companion object {
        val FILE: IFileElementType = IFileElementType(AngelscriptLanguage.INSTANCE)
    }
}

// ─── Named element infrastructure ────────────────────────────────────────────

interface AngelscriptNamedElement : PsiNameIdentifierOwner

abstract class AngelscriptNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), AngelscriptNamedElement {
    override fun getNameIdentifier(): PsiElement? =
        node.findChildByType(AngelscriptTypes.IDENTIFIER)?.psi

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(newName: String): PsiElement {
        val nameId = nameIdentifier ?: return this
        val newId = AngelscriptElementFactory.createIdentifier(project, newName)
        nameId.replace(newId)
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()

    override fun getUseScope(): SearchScope =
        GlobalSearchScope.getScopeRestrictedByFileTypes(
            GlobalSearchScope.projectScope(project),
            AngelscriptFileType.INSTANCE
        )
}

// ─── Element factory ─────────────────────────────────────────────────────────

object AngelscriptElementFactory {
    /** Creates a fresh IDENTIFIER leaf with the given [name]. */
    fun createIdentifier(project: Project, name: String): PsiElement {
        val file = createFile(project, "int $name;")
        return file.firstChild          // VariableDecl
            .node.getChildren(null)
            .first { it.elementType == AngelscriptTypes.IDENTIFIER }
            .psi
    }

    private fun createFile(project: Project, text: String): AngelscriptFile =
        PsiFileFactory.getInstance(project)
            .createFileFromText("dummy.as", AngelscriptFileType.INSTANCE, text)
            as AngelscriptFile
}

// ─── PsiImplUtil helpers (for rules that can't use the base class directly) ───

object AngelscriptPsiImplUtil {

    @JvmStatic
    fun getName(element: AngelscriptVariableAccessExpr): String? =
        element.node.findChildByType(AngelscriptTypes.IDENTIFIER)?.text

    // EventDecl / DelegateDecl / MixinDecl — delegate to the inner FunctionDecl

    @JvmStatic fun getName(element: AngelscriptEventDecl): String? = element.functionDecl.name
    @JvmStatic fun getNameIdentifier(element: AngelscriptEventDecl): PsiElement? = element.functionDecl.nameIdentifier

    @JvmStatic fun getName(element: AngelscriptDelegateDecl): String? = element.functionDecl.name
    @JvmStatic fun getNameIdentifier(element: AngelscriptDelegateDecl): PsiElement? = element.functionDecl.nameIdentifier

    @JvmStatic fun getName(element: AngelscriptMixinDecl): String? = element.functionDecl.name
    @JvmStatic fun getNameIdentifier(element: AngelscriptMixinDecl): PsiElement? = element.functionDecl.nameIdentifier

    // NamespaceDecl — NamespacePath is a private rule; its IDENTIFIER/COLONCOLON tokens
    // are flat children of NamespaceDecl between NAMESPACE_KW and LBRACE.

    @JvmStatic
    fun getName(element: AngelscriptNamespaceDecl): String? {
        val sb = StringBuilder()
        for (child in element.node.getChildren(null)) {
            when (child.elementType) {
                AngelscriptTypes.IDENTIFIER -> sb.append(child.text)
                AngelscriptTypes.COLONCOLON -> sb.append("::")
                AngelscriptTypes.LBRACE    -> break
            }
        }
        return if (sb.isEmpty()) null else sb.toString()
    }

    @JvmStatic
    fun getNameIdentifier(element: AngelscriptNamespaceDecl): PsiElement? =
        element.node.getChildren(null)
            .firstOrNull { it.elementType == AngelscriptTypes.IDENTIFIER }
            ?.psi
}

// ─── Reference ───────────────────────────────────────────────────────────────

internal class AngelscriptReference(
    element: PsiElement,
    textRange: TextRange,
    private val name: String,
) : PsiPolyVariantReferenceBase<PsiElement>(element, textRange) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        // Local/field resolution first — innermost scope wins, no project scan needed
        val local = resolveLocally()
        if (local.isNotEmpty()) return local.toTypedArray()

        val project = myElement.project
        val scope = GlobalSearchScope.projectScope(project)

        // AngelScript-wide global scan
        val results = mutableListOf<ResolveResult>()
        val allFiles = FileTypeIndex.getFiles(AngelscriptFileType.INSTANCE, scope)
        for (vFile in allFiles) {
            try {
                val psiFile = PsiManager.getInstance(project).findFile(vFile) ?: continue
                collectDeclarations(psiFile, name, results)
            } catch (e: Exception) {}
        }
        if (results.isNotEmpty()) return results.toTypedArray()

        // Fall back to C++ header search (only if enabled in settings)
        val settings = AngelscriptSettings.getInstance()
        if (settings.cppHeaderResolutionEnabled) {
            return resolveCpp(project, scope, settings).toTypedArray()
        }
        return emptyArray()
    }

    /** Searches C++ header files in parallel for a declaration matching [name]. Results are cached by name+patterns. */
    private fun resolveCpp(project: Project, scope: GlobalSearchScope, settings: AngelscriptSettings): List<ResolveResult> {
        val key = AngelscriptCppCache.CacheKey(
            name            = name,
            functionPattern = settings.cppFunctionPattern,
            classPattern    = settings.cppClassPattern,
            enumPattern     = settings.cppEnumPattern,
        )
        val cppCache = AngelscriptCppCache.getInstance(project)

        // Cache hit: return live pointers, or empty list for a cached "not found"
        val cached = cppCache.get(key)
        if (cached != null) {
            val live = cached.mapNotNull { it.element?.takeIf { e -> e.isValid } }
            if (cached.isEmpty() || live.isNotEmpty()) return live.map { PsiElementResolveResult(it) }
            cppCache.clear() // all pointers died (files deleted) — fall through to re-scan
        }

        // Cache miss: compile patterns and scan
        fun tryCompile(template: String): Regex? = try { settings.compileCppPattern(template, name) } catch (_: Exception) { null }
        val functionPattern = tryCompile(settings.cppFunctionPattern)
        val classPattern    = tryCompile(settings.cppClassPattern)
        val enumPattern     = tryCompile(settings.cppEnumPattern)
        if (functionPattern == null && classPattern == null && enumPattern == null) return emptyList()

        val headerFiles = (FilenameIndex.getAllFilesByExt(project, "h", scope) +
                           FilenameIndex.getAllFilesByExt(project, "hpp", scope))

        if (headerFiles.isEmpty()) return emptyList()

        val elements = ConcurrentLinkedQueue<PsiElement>()

        // Use the common ForkJoinPool for parallel file scanning
        ForkJoinPool.commonPool().submit {
            headerFiles.toList().parallelStream().forEach { vFile ->
                try {
                    ApplicationManager.getApplication().runReadAction {
                        val psiFile = PsiManager.getInstance(project).findFile(vFile)
                            ?: return@runReadAction
                        val text = psiFile.text
                        val match = functionPattern?.find(text)
                            ?: classPattern?.find(text)
                            ?: enumPattern?.find(text)
                            ?: return@runReadAction
                        val nameOffset = match.value.lastIndexOf(name)
                        val leaf = psiFile.findElementAt(match.range.first + nameOffset)
                        if (leaf != null) elements.add(leaf)
                    }
                } catch (_: Exception) {}
            }
        }.get() // block until all parallel tasks finish

        // Store in cache (empty list caches "not found" to avoid repeat scans)
        val pointers = elements.map { SmartPointerManager.getInstance(project).createSmartPsiElementPointer(it) }
        cppCache.put(key, pointers)

        return elements.map { PsiElementResolveResult(it) }
    }

    private fun resolveLocally(): List<ResolveResult> {
        val results = mutableListOf<ResolveResult>()
        val refOffset = myElement.textOffset
        var node: PsiElement? = myElement.parent
        while (node != null && node !is PsiFile) {
            when (node) {
                is AngelscriptCompoundStatement -> {
                    // Only locals declared textually before the reference are visible
                    node.variableDeclList
                        .filter { it.name == name && it.textOffset < refOffset }
                        .forEach { results.add(PsiElementResolveResult(it)) }
                }
                is AngelscriptFunctionDecl -> {
                    node.parameterList.parameterDeclList
                        .filter { it.name == name }
                        .forEach { results.add(PsiElementResolveResult(it)) }
                    break  // don't look past function boundary
                }
                is AngelscriptConstructorDecl -> {
                    node.parameterList.parameterDeclList
                        .filter { it.name == name }
                        .forEach { results.add(PsiElementResolveResult(it)) }
                    break
                }
                is AngelscriptClassDecl -> {
                    node.variableDeclList
                        .filter { it.name == name }
                        .forEach { results.add(PsiElementResolveResult(it)) }
                }
                is AngelscriptStructDecl -> {
                    node.variableDeclList
                        .filter { it.name == name }
                        .forEach { results.add(PsiElementResolveResult(it)) }
                }
                is AngelscriptInterfaceDecl -> {
                    node.variableDeclList
                        .filter { it.name == name }
                        .forEach { results.add(PsiElementResolveResult(it)) }
                }
            }
            node = node.parent
        }
        return results
    }

    override fun getVariants(): Array<Any> = emptyArray()

    override fun handleElementRename(newName: String): PsiElement {
        val nameLeaf = myElement.node.findChildByType(AngelscriptTypes.IDENTIFIER)?.psi
            ?: return myElement
        val newId = AngelscriptElementFactory.createIdentifier(myElement.project, newName)
        nameLeaf.replace(newId)
        return myElement
    }

    companion object {
        fun collectDeclarations(scope: PsiElement, name: String, results: MutableList<ResolveResult>) {
            for (child in scope.children) {
                if (child is AngelscriptNamedElement && child.name == name) {
                    results.add(PsiElementResolveResult(child))
                }
                // Recurse into containers
                if (child is AngelscriptNamespaceDecl ||
                    child is AngelscriptClassDecl ||
                    child is AngelscriptStructDecl ||
                    child is AngelscriptInterfaceDecl) {
                    collectDeclarations(child, name, results)
                }
            }
        }
    }
}

// ─── Reference mixins ────────────────────────────────────────────────────────

abstract class AngelscriptVariableAccessExprMixin(node: ASTNode)
    : ASTWrapperPsiElement(node), AngelscriptVariableAccessExpr {

    override fun getReference(): PsiReference? {
        val identNode = node.findChildByType(AngelscriptTypes.IDENTIFIER) ?: return null
        val name = identNode.text
        val range = TextRange.from(identNode.startOffsetInParent, identNode.textLength)
        return AngelscriptReference(this, range, name)
    }
}

abstract class AngelscriptTypeRefMixin(node: ASTNode)
    : ASTWrapperPsiElement(node), AngelscriptTypeRef {

    override fun getReference(): PsiReference? {
        // Walk past CONST token and ScopeRef child to reach the DataType IDENTIFIER
        val identNode = node.getChildren(null).firstOrNull { child ->
            child.elementType != AngelscriptTypes.CONST &&
            child.elementType != TokenType.WHITE_SPACE &&
            child.psi !is AngelscriptScopeRef &&
            child.elementType == AngelscriptTypes.IDENTIFIER
        } ?: return null
        val name = identNode.text
        val range = TextRange.from(identNode.startOffsetInParent, identNode.textLength)
        return AngelscriptReference(this, range, name)
    }
}
