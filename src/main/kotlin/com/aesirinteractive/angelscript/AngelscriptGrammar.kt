package com.aesirinteractive.angelscript

import com.aesirinteractive.angelscript.psi.AngelscriptDelegateDecl
import com.aesirinteractive.angelscript.psi.AngelscriptEventDecl
import com.aesirinteractive.angelscript.psi.AngelscriptMixinDecl
import com.aesirinteractive.angelscript.psi.AngelscriptNamespaceDecl
import com.aesirinteractive.angelscript.psi.AngelscriptVariableAccessExpr
import com.aesirinteractive.angelscript.psi.AngelscriptVariableDecl
import com.intellij.codeInsight.lookup.LookupElement
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
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.util.ProcessingContext
import kotlin.jvm.java


class AngelscriptTokenType(debugName: String) : IElementType(debugName, AngelscriptLanguage.INSTANCE) {

    override fun toString(): String {
        return "AngelscriptTokenType." + super.toString()
    }
}

class AngelscriptElementType(debugName: String) : IElementType(debugName, AngelscriptLanguage.INSTANCE)

class AngelscriptLexerAdapter : FlexAdapter(AngelscriptLexer(null))

class AngelscriptFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, AngelscriptLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return AngelscriptFileType.INSTANCE
    }

    override fun toString(): String {
        return "Angelscript File"
    }
}

interface AngelscriptTokenSets {
    companion object {
        val IDENTIFIERS: TokenSet = TokenSet.create(AngelscriptTypes.IDENTIFIER)

        val COMMENTS: TokenSet = TokenSet.create(AngelscriptTypes.COMMENT)
    }
}
internal class AngelscriptParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer {
        return AngelscriptLexerAdapter()
    }

    override fun getCommentTokens(): TokenSet {
        return AngelscriptTokenSets.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project?): PsiParser {
        return AngelscriptParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return AngelscriptFile(viewProvider)
    }

    public override fun createElement(node: ASTNode?): PsiElement {
        return AngelscriptTypes.Factory.createElement(node)
    }

    companion object {
        val FILE: IFileElementType = IFileElementType(AngelscriptLanguage.INSTANCE)
    }
}

interface AngelscriptNamedElement : PsiNameIdentifierOwner

abstract class AngelscriptNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), AngelscriptNamedElement {
    override fun getNameIdentifier(): PsiElement? =
        node.findChildByType(AngelscriptTypes.IDENTIFIER)?.psi

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(newName: String): PsiElement {
        // TODO: implement rename via element factory once one exists
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()
}

object AngelscriptPsiImplUtil {

    @JvmStatic
    fun getName(element: AngelscriptVariableAccessExpr): String? {
        val keyNode: ASTNode? = element.node.findChildByType(AngelscriptTypes.IDENTIFIER)
        return keyNode?.text
    }

    // EventDecl / DelegateDecl / MixinDecl — delegate to the inner FunctionDecl

    @JvmStatic
    fun getName(element: AngelscriptEventDecl): String? = element.functionDecl.name

    @JvmStatic
    fun getNameIdentifier(element: AngelscriptEventDecl): PsiElement? = element.functionDecl.nameIdentifier

    @JvmStatic
    fun getName(element: AngelscriptDelegateDecl): String? = element.functionDecl.name

    @JvmStatic
    fun getNameIdentifier(element: AngelscriptDelegateDecl): PsiElement? = element.functionDecl.nameIdentifier

    @JvmStatic
    fun getName(element: AngelscriptMixinDecl): String? = element.functionDecl.name

    @JvmStatic
    fun getNameIdentifier(element: AngelscriptMixinDecl): PsiElement? = element.functionDecl.nameIdentifier

    // NamespaceDecl — NamespacePath is a private rule, so its IDENTIFIER and COLONCOLON
    // tokens are flat children of NamespaceDecl between NAMESPACE_KW and LBRACE.
    // getName() joins them as "A::B"; getNameIdentifier() returns the first IDENTIFIER.

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

//object AngelscriptElementFactory {
////    fun createVariable(project: Project, name: String?): SimpleProperty? {
////        val file: SimpleFile = createFile(project, name)
////        return file.getFirstChild() as SimpleProperty?
////    }
////
////    fun createFile(project: Project, text: String?): SimpleFile {
////        val name = "dummy.simple"
////        return PsiFileFactory.getInstance(project).createFileFromText
////        (name, SimpleFileType.INSTANCE, text) as SimpleFile
////    }
//}

internal class AngelscriptReference(element: PsiElement, textRange: TextRange) : PsiPolyVariantReferenceBase<PsiElement?>(element, textRange) {
    private val key: String

    init {
        key = element.getText()
            .substring(textRange.getStartOffset(), textRange.getEndOffset())
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult?> {
        val project = myElement!!.project
//        val properties: MutableList<SimpleProperty> = SimpleUtil.findProperties(project, key)
        val results: MutableList<ResolveResult?> = ArrayList<ResolveResult?>()
//        for (property in properties) {
//            results.add(PsiElementResolveResult(property))
//        }
        return results.toTypedArray<ResolveResult?>()
    }

    override fun getVariants(): Array<Any?> {
        val project = myElement!!.project
//        val properties: MutableList<SimpleProperty> = SimpleUtil.findProperties(project)
        val variants: MutableList<LookupElement?> = ArrayList<LookupElement?>()
//        for (property in properties) {
//            if (property.getKey() != null && !property.getKey().isEmpty()) {
//                variants.add(
//                    LookupElementBuilder
//                        .create(property).withIcon(SimpleIcons.FILE)
//                        .withTypeText(property.getContainingFile().getName())
//                )
//            }
//        }
        return variants.toTypedArray()
    }
}

internal class AngelscriptReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(PsiNameIdentifierOwner::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<out PsiReference> {
//                    val literalExpression: PsiLiteralExpression = element as PsiLiteralExpression
//                    val value = if (literalExpression.getValue() is String) literalExpression.getValue() as String? else null
//                    if ((value != null && value.startsWith(SIMPLE_PREFIX_STR + SIMPLE_SEPARATOR_STR))) {
//                        val property = TextRange(
//                            SIMPLE_PREFIX_STR.length() + SIMPLE_SEPARATOR_STR.length() + 1,
//                            value.length + 1
//                        )
//                        return arrayOf<PsiReference>(SimpleReference(element, property))
//                    }
                    return PsiReference.EMPTY_ARRAY
                }
            })
    }
}
