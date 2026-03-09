package com.aesirinteractive.angelscript

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet


class AngelscriptTokenType(debugName: String) : IElementType(debugName, AngelscriptLanguage.INSTANCE) {

    override fun toString(): String {
        return "AngelscriptTokenType." + super.toString()
    }
}

class AngelscriptElementType(debugName: String) : IElementType(debugName, AngelscriptLanguage.INSTANCE)

class AngelscriptLexerAdapter : FlexAdapter(AngelscriptLexer(null))

class SimpleFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, AngelscriptLanguage.INSTANCE) {
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

        val COMMENTS: TokenSet = TokenSet.create(AngelscriptTypes.ARGUMENT)
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
        return SimpleFile(viewProvider)
    }

    public override fun createElement(node: ASTNode?): PsiElement {
        return AngelscriptTypes.Factory.createElement(node)
    }

    companion object {
        val FILE: IFileElementType = IFileElementType(AngelscriptLanguage.INSTANCE)
    }
}


