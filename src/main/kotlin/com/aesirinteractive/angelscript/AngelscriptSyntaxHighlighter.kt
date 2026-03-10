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

        private val PREPROCESSOR: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_PREPROCESSOR", DefaultLanguageHighlighterColors.METADATA)

        private val BAD_CHAR_KEYS = arrayOf<TextAttributesKey>(BAD_CHARACTER)
        private val COMMENT_KEYS = arrayOf<TextAttributesKey>(COMMENT)
        private val PREPROCESSOR_KEYS = arrayOf<TextAttributesKey>(PREPROCESSOR)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()

        // Lexer-level token highlighting (token types only — no PSI node types here)
        public val TOKEN_KEYS: Map<IElementType, Array<TextAttributesKey>> = mapOf(
            AngelscriptTypes.IDENTIFIER to uniqueKey("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER),
            AngelscriptTypes.NUMBER_LITERAL to uniqueKey("NUMBER_LITERAL", DefaultLanguageHighlighterColors.NUMBER),
            AngelscriptTypes.STRING_LITERAL to uniqueKey("STRING_LITERAL", DefaultLanguageHighlighterColors.STRING),
            AngelscriptTypes.BOOL_LITERAL to uniqueKey("BOOL_LITERAL", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.NULLPTR_KW to uniqueKey("NULLPTR_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.COMMENT to COMMENT_KEYS,
            // Control flow
            AngelscriptTypes.BREAK to uniqueKey("BREAK", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CONTINUE to uniqueKey("CONTINUE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.ELSE to uniqueKey("ELSE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.FOR to uniqueKey("FOR", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.IF to uniqueKey("IF", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.RETURN to uniqueKey("RETURN", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.WHILE to uniqueKey("WHILE", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.SWITCH_KW to uniqueKey("SWITCH_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CASE_KW to uniqueKey("CASE_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.DEFAULT_KW to uniqueKey("DEFAULT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.DO_KW to uniqueKey("DO_KW", DefaultLanguageHighlighterColors.KEYWORD),
            // Declaration keywords
            AngelscriptTypes.CONST to uniqueKey("CONST", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.ENUM to uniqueKey("ENUM", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.PUBLIC to uniqueKey("PUBLIC", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.STATIC to uniqueKey("STATIC", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.STRUCT to uniqueKey("STRUCT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.CLASS to uniqueKey("CLASS", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.SUPER to uniqueKey("SUPER", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.TYPEDEF_KW to uniqueKey("TYPEDEF_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.NAMESPACE_KW to uniqueKey("NAMESPACE_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.EVENT_KW to uniqueKey("EVENT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.DELEGATE_KW to uniqueKey("DELEGATE_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.MIXIN_KW to uniqueKey("MIXIN_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.SHARED_KW to uniqueKey("SHARED_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.EXTERNAL_KW to uniqueKey("EXTERNAL_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.PRIVATE_KW to uniqueKey("PRIVATE_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.PROTECTED_KW to uniqueKey("PROTECTED_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.OVERRIDE_KW to uniqueKey("OVERRIDE_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.FINAL_KW to uniqueKey("FINAL_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.EXPLICIT_KW to uniqueKey("EXPLICIT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.PROPERTY_KW to uniqueKey("PROPERTY_KW", DefaultLanguageHighlighterColors.KEYWORD),
            // Type modifier keywords
            AngelscriptTypes.IN to uniqueKey("IN", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.OUT to uniqueKey("OUT", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.INOUT_KW to uniqueKey("INOUT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.AUTO to uniqueKey("AUTO", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.AUTO_KW to uniqueKey("AUTO_KW", DefaultLanguageHighlighterColors.KEYWORD),
            // Primitive type keywords
            AngelscriptTypes.VOID_KW to uniqueKey("VOID_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.INT_KW to uniqueKey("INT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.INT8_KW to uniqueKey("INT8_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.INT16_KW to uniqueKey("INT16_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.INT32_KW to uniqueKey("INT32_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.INT64_KW to uniqueKey("INT64_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.UINT_KW to uniqueKey("UINT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.UINT8_KW to uniqueKey("UINT8_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.UINT16_KW to uniqueKey("UINT16_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.UINT32_KW to uniqueKey("UINT32_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.UINT64_KW to uniqueKey("UINT64_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.FLOAT_KW to uniqueKey("FLOAT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.DOUBLE_KW to uniqueKey("DOUBLE_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.BOOL_KW to uniqueKey("BOOL_KW", DefaultLanguageHighlighterColors.KEYWORD),
            // Word operators
            AngelscriptTypes.NOT_KW to uniqueKey("NOT_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.AND_KW to uniqueKey("AND_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.OR_KW to uniqueKey("OR_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.XOR_KW to uniqueKey("XOR_KW", DefaultLanguageHighlighterColors.KEYWORD),
            AngelscriptTypes.IS_KW to uniqueKey("IS_KW", DefaultLanguageHighlighterColors.KEYWORD),
            // Unreal metadata
            AngelscriptTypes.UFUNCTION_KW to uniqueKey("UFUNCTION_KW", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.UPROPERTY_KW to uniqueKey("UPROPERTY_KW", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.UCLASS_KW to uniqueKey("UCLASS_KW", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.USTRUCT_KW to uniqueKey("USTRUCT_KW", DefaultLanguageHighlighterColors.METADATA),
            AngelscriptTypes.UENUM_KW to uniqueKey("UENUM_KW", DefaultLanguageHighlighterColors.METADATA),
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
            AngelscriptTypes.GTEQ to uniqueKey("GTEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.LTEQ to uniqueKey("LTEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.LTLT to uniqueKey("LTLT", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.POWER to uniqueKey("POWER", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.ANDAND to uniqueKey("ANDAND", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.OROR to uniqueKey("OROR", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.PLUSEQ to uniqueKey("PLUSEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MINUSEQ to uniqueKey("MINUSEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.MULEQ to uniqueKey("MULEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.POWEREQ to uniqueKey("POWEREQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.DIVEQ to uniqueKey("DIVEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.REMEQ to uniqueKey("REMEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.ANDEQ to uniqueKey("ANDEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.OREQ to uniqueKey("OREQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.XOREQ to uniqueKey("XOREQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.LSHIFTEQ to uniqueKey("LSHIFTEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.RSHIFTEQ to uniqueKey("RSHIFTEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
            AngelscriptTypes.RRSHIFTEQ to uniqueKey("RRSHIFTEQ", DefaultLanguageHighlighterColors.OPERATION_SIGN),
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
            // Preprocessor directives
            AngelscriptTypes.PP_IF to PREPROCESSOR_KEYS,
            AngelscriptTypes.PP_ELIF to PREPROCESSOR_KEYS,
            AngelscriptTypes.PP_ELSE to PREPROCESSOR_KEYS,
            AngelscriptTypes.PP_ENDIF to PREPROCESSOR_KEYS,
            AngelscriptTypes.PP_DEFINE to PREPROCESSOR_KEYS,
        )

        // Semantic color keys — used by AngelscriptAnnotator, not by the lexer highlighter
        val CLASS_NAME_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_CLASS_NAME", DefaultLanguageHighlighterColors.CLASS_NAME)
        val FUNCTION_DECLARATION_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_FUNCTION_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
        val FUNCTION_CALL_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_FUNCTION_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val PARAMETER_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_PARAMETER", DefaultLanguageHighlighterColors.PARAMETER)
        val INSTANCE_FIELD_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_INSTANCE_FIELD", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val TYPE_REF_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_TYPE_REF", DefaultLanguageHighlighterColors.CLASS_REFERENCE)
        val UNREAL_TYPE_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_UNREAL_TYPE", DefaultLanguageHighlighterColors.CLASS_REFERENCE)
        val LOCAL_VARIABLE_KEY: TextAttributesKey =
            createTextAttributesKey("ANGELSCRIPT_LOCAL_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE)

        val SEMANTIC_KEYS: Map<String, TextAttributesKey> = mapOf(
            "Class/struct name"       to CLASS_NAME_KEY,
            "Function declaration"    to FUNCTION_DECLARATION_KEY,
            "Function call"           to FUNCTION_CALL_KEY,
            "Parameter"               to PARAMETER_KEY,
            "Instance field"          to INSTANCE_FIELD_KEY,
            "Type reference"          to TYPE_REF_KEY,
            "Unreal type (U/A/F/E/T)" to UNREAL_TYPE_KEY,
            "Local variable"          to LOCAL_VARIABLE_KEY,
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
// Line comment
#define WITH_EDITOR

typedef float float32;

namespace Math
{
    float <funcDecl>Lerp</funcDecl>(float <param>a</param>, float <param>b</param>, float <param>t</param>)
    {
        return <param>a</param> + (<param>b</param> - <param>a</param>) * <param>t</param>;
    }
}

enum <unrealType>EItemRarity</unrealType> { Common, Uncommon, Rare = 10 }

UENUM()
enum <unrealType>ESlotType</unrealType> { Equipment, Consumable }

struct <unrealType>FItemData</unrealType>
{
    int <field>Count</field>;
    float <field>Weight</field>;
}

UCLASS()
class <unrealType>UInventoryComponent</unrealType> : <unrealType>UActorComponent</unrealType>
{
    UPROPERTY(EditAnywhere)
    <unrealType>TArray</unrealType><<unrealType>FItemData</unrealType>> <field>Items</field>;

    UPROPERTY(BlueprintReadOnly, meta=(ClampMin=0))
    int <field>Capacity</field> = 20;

    default <localVar>Capacity</localVar> = 10;

    <funcDecl>UInventoryComponent</funcDecl>()
    {
    }

    UFUNCTION(BlueprintCallable)
    bool <funcDecl>AddItem</funcDecl>(const <unrealType>FItemData</unrealType>& <param>Item</param>, int <param>Count</param> = 1)
    {
        if (<localVar>Items</localVar>.<funcCall>Num</funcCall>() >= <localVar>Capacity</localVar>)
            return false;
        <localVar>Items</localVar>.<funcCall>Add</funcCall>(<param>Item</param>);
        return true;
    }

    private int <funcDecl>FindSlot</funcDecl>(<unrealType>EItemRarity</unrealType> <param>Rarity</param>) const
    {
        // Local variable declarations
        int <localVar>index</localVar> = 0;
        float <localVar>best</localVar> = 0.0, <localVar>worst</localVar> = 1.0;
        auto <localVar>count</localVar> = <localVar>Items</localVar>.<funcCall>Num</funcCall>();
        <unrealType>FItemData</unrealType>@ <localVar>found</localVar> = nullptr;

        // for / foreach / while / do-while
        for (int <localVar>i</localVar> = 0; <localVar>i</localVar> < <localVar>count</localVar>; <localVar>i</localVar>++)
        {
            <unrealType>FItemData</unrealType> <localVar>entry</localVar> = <localVar>Items</localVar>[<localVar>i</localVar>];
            if (<localVar>entry</localVar>.<field>Weight</field> > <localVar>best</localVar>)
                <localVar>best</localVar> = <localVar>entry</localVar>.<field>Weight</field>;
        }

        for (<unrealType>FItemData</unrealType> <localVar>item</localVar> : <localVar>Items</localVar>)
        {
            <localVar>index</localVar>++;
            if (<localVar>item</localVar>.<field>Count</field> == 0)
                continue;
            if (<localVar>item</localVar>.<field>Count</field> > 5)
                break;
        }

        while (<localVar>index</localVar> > 0)
            <localVar>index</localVar>--;

        do { <localVar>index</localVar>++; } while (<localVar>index</localVar> < 3);

        // switch / case / default
        switch (<param>Rarity</param>)
        {
            case <unrealType>EItemRarity</unrealType>::<typeRef>Common</typeRef>:
                return 0;
            case <unrealType>EItemRarity</unrealType>::<typeRef>Rare</typeRef>:
                return 1;
            default:
                return -1;
        }
    }

    protected <unrealType>FVector</unrealType> <funcDecl>GetOffset</funcDecl>(int <param>slot</param>) override
    {
        // Arithmetic, bitwise, shift, logical operators
        int <localVar>x</localVar> = (<param>slot</param> + 2) * 3 - 1;
        int <localVar>flags</localVar> = <localVar>x</localVar> & 0xFF | (<localVar>x</localVar> ^ 0x0F) << 2;
        bool <localVar>ok</localVar> = <localVar>x</localVar> > 0 && <localVar>flags</localVar> != 0 || not false;

        // Ternary, assignment operators, unreal-type cast
        float <localVar>t</localVar> = <localVar>ok</localVar> ? 1.0 : 0.0;
        <localVar>t</localVar> += <typeRef>Math</typeRef>::<funcCall>Lerp</funcCall>(0.0, 1.0, <localVar>t</localVar>);
        <unrealType>FVector</unrealType> <localVar>result</localVar> = <unrealType>FVector</unrealType>(<localVar>t</localVar>, 0.0, 0.0);

        // C-style cast and generic call
        int <localVar>casted</localVar> = int(<localVar>t</localVar>);
        <unrealType>UObject</unrealType>@ <localVar>obj</localVar> = <funcCall>Cast</funcCall><<unrealType>UObject</unrealType>>(<localVar>result</localVar>);
        <unrealType>AActor</unrealType>@ <localVar>actor</localVar> = <funcCall>Cast</funcCall><<unrealType>AActor</unrealType>>(<localVar>obj</localVar>);

        // String and bool literals, nullptr, string concat
        string <localVar>msg</localVar> = "Hello" " World";
        bool <localVar>flag</localVar> = true;
        <unrealType>UObject</unrealType>@ <localVar>none</localVar> = nullptr;

        // PostfixExpr: scope suffix, chained field/method, subscript, postfix update
        <localVar>result</localVar>.<field>X</field> = <unrealType>FMath</unrealType>::<funcCall>Abs</funcCall>(<localVar>t</localVar>);
        <localVar>result</localVar>.<field>Y</field> = <localVar>Items</localVar>[0].<field>Weight</field>;
        <localVar>result</localVar>.<field>Z</field> = <localVar>t</localVar>++;

        // Init-list expression
        <unrealType>FItemData</unrealType> <localVar>blank</localVar> = <unrealType>FItemData</unrealType> = { 0, 0.0 };

        // is / !is operators
        bool <localVar>isActor</localVar> = <localVar>obj</localVar> is <unrealType>AActor</unrealType>;
        bool <localVar>notActor</localVar> = <localVar>obj</localVar> !is <unrealType>AActor</unrealType>;

        // Named argument
        <funcCall>AddItem</funcCall>(Item: <localVar>blank</localVar>, Count: 1);

        return <localVar>result</localVar>;
    }
}

event void <funcDecl>OnItemAdded</funcDecl>(<unrealType>FItemData</unrealType> <param>item</param>);
delegate void <funcDecl>FItemDelegate</funcDecl>(<unrealType>FItemData</unrealType> <param>item</param>);

mixin void <funcDecl>PrintCount</funcDecl>()
{
    int <localVar>n</localVar> = <funcCall>GetCount</funcCall>();
}

#if WITH_EDITOR
void <funcDecl>EditorOnly</funcDecl>()
{
    int <localVar>x</localVar> = 1;
}
#endif
        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): kotlin.collections.MutableMap<kotlin.String?, TextAttributesKey?> {
        return mutableMapOf(
            "className"  to AngelscriptSyntaxHighlighter.CLASS_NAME_KEY,
            "funcDecl"   to AngelscriptSyntaxHighlighter.FUNCTION_DECLARATION_KEY,
            "funcCall"   to AngelscriptSyntaxHighlighter.FUNCTION_CALL_KEY,
            "param"      to AngelscriptSyntaxHighlighter.PARAMETER_KEY,
            "field"      to AngelscriptSyntaxHighlighter.INSTANCE_FIELD_KEY,
            "typeRef"    to AngelscriptSyntaxHighlighter.TYPE_REF_KEY,
            "unrealType" to AngelscriptSyntaxHighlighter.UNREAL_TYPE_KEY,
            "localVar"   to AngelscriptSyntaxHighlighter.LOCAL_VARIABLE_KEY,
        )
    }

    override fun getAttributeDescriptors(): kotlin.Array<AttributesDescriptor?> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): kotlin.Array<ColorDescriptor?> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): kotlin.String {
        return "AngelScript"
    }

    companion object {
        private val DESCRIPTORS: kotlin.Array<AttributesDescriptor?> =
            (TOKEN_KEYS.entries.map { (token, keys) ->
                AttributesDescriptor(token.toString(), keys.first())
            } + AngelscriptSyntaxHighlighter.SEMANTIC_KEYS.entries.map { (name, key) ->
                AttributesDescriptor(name, key)
            }).toTypedArray()
    }
}
