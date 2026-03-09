package com.aesirinteractive.angelscript

import com.aesirinteractive.angelscript.AngelscriptSyntaxHighlighter.Companion.TOKEN_KEYS
import com.intellij.icons.AllIcons
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.util.containers.stream
import javax.swing.Icon


class AngelscriptSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return AngelscriptLexerAdapter()
    }

    public override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        if (tokenType == TokenType.BAD_CHARACTER) return BAD_CHAR_KEYS
        return TOKEN_KEYS[tokenType] ?: EMPTY_KEYS
    }

    companion object {
        private val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val COMMENT: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)

        private fun uniqueKey(typeName: String, fallback: TextAttributesKey): Array<TextAttributesKey> {
            return arrayOf(createTextAttributesKey("ANGELSCRIPT_$typeName", fallback))
        }

        private val BAD_CHAR_KEYS = arrayOf<TextAttributesKey>(BAD_CHARACTER)
        private val COMMENT_KEYS = arrayOf<TextAttributesKey>(COMMENT)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()

        public val TOKEN_KEYS: Map<IElementType, Array<TextAttributesKey>> = mapOf(
            // Lexer token types
            AngelscriptTypes.IDENTIFIER to uniqueKey("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER),
            AngelscriptTypes.NUMBER_LITERAL to uniqueKey("NUMBER_LITERAL", DefaultLanguageHighlighterColors.NUMBER),
            AngelscriptTypes.STRING_LITERAL to uniqueKey("STRING_LITERAL", DefaultLanguageHighlighterColors.STRING),
            AngelscriptTypes.BOOL_LITERAL to uniqueKey("BOOL_LITERAL", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.COMMENT to COMMENT_KEYS,
            AngelscriptTypes.CONST to uniqueKey("CONST", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.BREAK to uniqueKey("BREAK", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CONTINUE to uniqueKey("CONTINUE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.ELSE to uniqueKey("ELSE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.ENUM to uniqueKey("ENUM", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.FOR to uniqueKey("FOR", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.IF to uniqueKey("IF", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.IN to uniqueKey("IN", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.OUT to uniqueKey("OUT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.AUTO to uniqueKey("AUTO", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.WHILE to uniqueKey("WHILE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.PUBLIC to uniqueKey("PUBLIC", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.RETURN to uniqueKey("RETURN", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.STATIC to uniqueKey("STATIC", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.STRUCT to uniqueKey("STRUCT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CLASS to uniqueKey("CLASS", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.SUPER to uniqueKey("SUPER", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.EQ to uniqueKey("EQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.EQEQ to uniqueKey("EQEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.EXCLEQ to uniqueKey("EXCLEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.EXCL to uniqueKey("EXCL", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.PLUS to uniqueKey("PLUS", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MINUS to uniqueKey("MINUS", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MUL to uniqueKey("MUL", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.DIV to uniqueKey("DIV", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.REM to uniqueKey("REM", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.AND to uniqueKey("AND", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.OR to uniqueKey("OR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.XOR to uniqueKey("XOR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.LT to uniqueKey("LT", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.GT to uniqueKey("GT", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.PLUSEQ to uniqueKey("PLUSEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MINUSEQ to uniqueKey("MINUSEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MULEQ to uniqueKey("MULEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.DIVEQ to uniqueKey("DIVEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.REMEQ to uniqueKey("REMEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.ANDEQ to uniqueKey("ANDEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.OREQ to uniqueKey("OREQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.XOREQ to uniqueKey("XOREQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.COLON to uniqueKey("COLON", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.COLONCOLON to uniqueKey("COLONCOLON", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.DOT to uniqueKey("DOT", DefaultLanguageHighlighterColors.DOT),
            AngelscriptTypes.COMMA to uniqueKey("COMMA", DefaultLanguageHighlighterColors.COMMA),
            AngelscriptTypes.SEMICOLON to uniqueKey("SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON),
            AngelscriptTypes.LBRACE to uniqueKey("LBRACE", DefaultLanguageHighlighterColors.BRACES),
            AngelscriptTypes.RBRACE to uniqueKey("RBRACE", DefaultLanguageHighlighterColors.BRACES),
            AngelscriptTypes.LPAREN to uniqueKey("LPAREN", DefaultLanguageHighlighterColors.PARENTHESES),
            AngelscriptTypes.RPAREN to uniqueKey("RPAREN", DefaultLanguageHighlighterColors.PARENTHESES),
            AngelscriptTypes.LBRACK to uniqueKey("LBRACK", DefaultLanguageHighlighterColors.BRACKETS),
            AngelscriptTypes.RBRACK to uniqueKey("RBRACK", DefaultLanguageHighlighterColors.BRACKETS),

            // Parser node types
            AngelscriptTypes.CLASS_DECL to uniqueKey("CLASS_DECL", DefaultLanguageHighlighterColors.CLASS_NAME),
            AngelscriptTypes.STRUCT_DECL to uniqueKey("STRUCT_DECL", DefaultLanguageHighlighterColors.CLASS_NAME),
            AngelscriptTypes.CLASS_DECL to uniqueKey("CLASS_DECL", DefaultLanguageHighlighterColors.CLASS_NAME),
            AngelscriptTypes.FUNCTION_DECL to uniqueKey("FUNCTION_DECL", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION),
            AngelscriptTypes.PARAMETER_DECL to uniqueKey("PARAMETER_DECL", DefaultLanguageHighlighterColors.PARAMETER),
            AngelscriptTypes.TYPE_REF to uniqueKey("TYPE_REF", DefaultLanguageHighlighterColors.CLASS_REFERENCE),
            AngelscriptTypes.TYPE_ARGUMENT to uniqueKey("TYPE_ARGUMENT", DefaultLanguageHighlighterColors.CLASS_REFERENCE),
            AngelscriptTypes.TYPEDEF_DECL to uniqueKey("TYPEDEF_DECL", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.VARIABLE_DECL to uniqueKey("VARIABLE_DECL", DefaultLanguageHighlighterColors.LOCAL_VARIABLE),
            AngelscriptTypes.VARIABLE_ACCESS_EXPR to uniqueKey("VARIABLE_ACCESS_EXPR", DefaultLanguageHighlighterColors.LOCAL_VARIABLE),
            AngelscriptTypes.U_CLASS_DECL to uniqueKey("U_CLASS_DECL", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.U_STRUCT_DECL to uniqueKey("U_STRUCT_DECL", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.U_FUNCTION_DECL to uniqueKey("U_FUNCTION_DECL", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.U_PROPERTY_DECL to uniqueKey("U_PROPERTY_DECL", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.COMPOUND_STATEMENT to uniqueKey("COMPOUND_STATEMENT", DefaultLanguageHighlighterColors.BRACES),
            AngelscriptTypes.STATEMENT to uniqueKey("STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.EXPRESSION_STATEMENT to uniqueKey("EXPRESSION_STATEMENT", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.IF_STATEMENT to uniqueKey("IF_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.ELSE_CLAUSE to uniqueKey("ELSE_CLAUSE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.SWITCH_STATEMENT to uniqueKey("SWITCH_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CASE_STATEMENT to uniqueKey("CASE_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.DO_STATEMENT to uniqueKey("DO_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.WHILE_STATEMENT to uniqueKey("WHILE_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.FOR_STATEMENT to uniqueKey("FOR_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.FOREACH_STATEMENT to uniqueKey("FOREACH_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.BREAK_STATEMENT to uniqueKey("BREAK_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CONTINUE_STATEMENT to uniqueKey("CONTINUE_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.RETURN_STATEMENT to uniqueKey("RETURN_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.ADD_EXPR to uniqueKey("ADD_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.ASSIGNMENT_EXPR to uniqueKey("ASSIGNMENT_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.BITWISE_AND_EXPR to uniqueKey("BITWISE_AND_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.CAST_EXPR to uniqueKey("CAST_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.COMMA_EXPR to uniqueKey("COMMA_EXPR", DefaultLanguageHighlighterColors.COMMA),
            AngelscriptTypes.CONDITIONAL_EXPR to uniqueKey("CONDITIONAL_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.EQUALITY_EXPR to uniqueKey("EQUALITY_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.EXCLUSIVE_OR_EXPR to uniqueKey("EXCLUSIVE_OR_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.INCLUSIVE_OR_EXPR to uniqueKey("INCLUSIVE_OR_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.INIT_LIST_EXPR to uniqueKey("INIT_LIST_EXPR", DefaultLanguageHighlighterColors.BRACES),
            AngelscriptTypes.LOGICAL_AND_EXPR to uniqueKey("LOGICAL_AND_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.LOGICAL_OR_EXPR to uniqueKey("LOGICAL_OR_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MULTIPLY_EXPR to uniqueKey("MULTIPLY_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.PARENTHESIZED_EXPR to uniqueKey("PARENTHESIZED_EXPR", DefaultLanguageHighlighterColors.PARENTHESES),
            AngelscriptTypes.POSTFIX_EXPR to uniqueKey("POSTFIX_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.PRIMARY_EXPR to uniqueKey("PRIMARY_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.RELATIONAL_EXPR to uniqueKey("RELATIONAL_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.SHIFT_EXPR to uniqueKey("SHIFT_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.STRING_CONCAT_EXPR to uniqueKey("STRING_CONCAT_EXPR", DefaultLanguageHighlighterColors.STRING),
            AngelscriptTypes.UNARY_EXPR to uniqueKey("UNARY_EXPR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.SCOPE_REF to uniqueKey("SCOPE_REF", DefaultLanguageHighlighterColors.CLASS_REFERENCE),
            AngelscriptTypes.ARGUMENT to uniqueKey("ARGUMENT", DefaultLanguageHighlighterColors.PARAMETER),
            AngelscriptTypes.ARG_LIST to uniqueKey("ARG_LIST", DefaultLanguageHighlighterColors.PARENTHESES),
            AngelscriptTypes.PARAMETER_LIST to uniqueKey("PARAMETER_LIST", DefaultLanguageHighlighterColors.PARENTHESES),
            AngelscriptTypes.MIXIN_DECL to uniqueKey("MIXIN_DECL", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.EVENT_DECL to uniqueKey("EVENT_DECL", DefaultLanguageHighlighterColors.KEYWORD)
        )
    }
}

internal class AngelscriptSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return AngelscriptSyntaxHighlighter()
    }
}

internal class AngelscriptColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.AS;
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return AngelscriptSyntaxHighlighter()
    }

    override fun getDemoText(): kotlin.String {
        return """
delegate void FItemMoveCompleted(bool bCanceled);
mixin void FItemMoveCompleted(bool bCanceled);
event void FItemMoveCompleted(bool bCanceled);

struct FInventoryViewModel
{
	void bar(int a)
	{
	    if (true) {
	        AActor a = nullptr;
	        while (true) {
	            for (int32 i = 0; i < 10; i++) {
	            }
	        }
	    } 
	}
}

class UInventoryViewModel
{
	void bar(int a)
	{
	    if (true) {
	        AActor a = nullptr;
	        while (true) {
	            for (int32 i = 0; i < 10; i++) {
	            }
	        }
	    } 
	}
}
        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): kotlin.collections.MutableMap<kotlin.String?, TextAttributesKey?>? {
        return null
    }

    override fun getAttributeDescriptors():  kotlin.Array<AttributesDescriptor?> {
        return AngelscriptColorSettingsPage.Companion.DESCRIPTORS
    }

    override fun getColorDescriptors(): kotlin.Array<ColorDescriptor?> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): kotlin.String {
        return "AngelScript"
    }

    companion object {
        private val DESCRIPTORS: kotlin.Array<AttributesDescriptor?> =
            TOKEN_KEYS.entries.map {
                var attributes: TextAttributesKey = it.value.stream().findFirst().get()
                val attributesDescriptor = AttributesDescriptor(it.key.toString(), attributes)
                attributesDescriptor
            }.toTypedArray()

    }
}
