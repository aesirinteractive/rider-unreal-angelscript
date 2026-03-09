package com.aesirinteractive.angelscript;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.aesirinteractive.angelscript.AngelscriptTypes;
import com.intellij.psi.TokenType;

%%

%{
	public AngelscriptLexer() {
		this((java.io.Reader)null);
	}
%}

%class AngelscriptLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{
	return;
%eof}

//%s IN_BLOCK_COMMENT
%s IN_STRING_DQ
%s IN_STRING_SQ
%s IN_RAW_STRING

///////////////////////////////////////////////////////////////////////////////////////////////////
// Whitespace
///////////////////////////////////////////////////////////////////////////////////////////////////

EOL_WS           = \n | \r | \r\n
LINE_WS          = [\ \t\f]
WHITE_SPACE      = ({LINE_WS}|{EOL_WS})+

///////////////////////////////////////////////////////////////////////////////////////////////////
// Identifiers
///////////////////////////////////////////////////////////////////////////////////////////////////

IDENTIFIER       = [_\p{XID_Start}][_\p{XID_Continue}]*

///////////////////////////////////////////////////////////////////////////////////////////////////
// Numbers
///////////////////////////////////////////////////////////////////////////////////////////////////

DEC_DIGIT        = [0-9]
HEX_DIGIT        = [0-9a-fA-F]
BIN_DIGIT        = [01]
EXPONENT         = [eE][\+\-]?{DEC_DIGIT}+
DIGIT_SEP        = "'"

DEC_INTEGER      = {DEC_DIGIT}+({DIGIT_SEP}{DEC_DIGIT}+)*
HEX_INTEGER      = 0[xX]{HEX_DIGIT}+({DIGIT_SEP}{HEX_DIGIT}+)*
BIN_INTEGER      = 0[bB]{BIN_DIGIT}+({DIGIT_SEP}{BIN_DIGIT}+)*

FLOAT_NUMBER     = (
										 {DEC_INTEGER}\.{DEC_DIGIT}*({EXPONENT})?
									 | \.{DEC_DIGIT}+({EXPONENT})?
									 | {DEC_INTEGER}{EXPONENT}
									 )

///////////////////////////////////////////////////////////////////////////////////////////////////
// Strings and comments
///////////////////////////////////////////////////////////////////////////////////////////////////

ESCAPE_SEQUENCE  = \\([^xuU] | [0-9]{2,3} | x{HEX_DIGIT}{2} | u{HEX_DIGIT}{4} | U{HEX_DIGIT}{8})
RAW_PREFIX       = [A-Za-z_][A-Za-z0-9_]*

%%

<YYINITIAL> {
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Trivia
	///////////////////////////////////////////////////////////////////////////////////////////////////

	{WHITE_SPACE}                    { return TokenType.WHITE_SPACE; }

	"//" .*                    { return AngelscriptTypes.COMMENT; }

//	"/*"                             { yybegin(IN_BLOCK_COMMENT); return AngelscriptTypes.COMMENT; }

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Strings
	///////////////////////////////////////////////////////////////////////////////////////////////////

	"\"\"\""                        { yybegin(IN_RAW_STRING); return AngelscriptTypes.STRING_LITERAL; }
	{RAW_PREFIX}"\"\"\""             { yybegin(IN_RAW_STRING); return AngelscriptTypes.STRING_LITERAL; }

	"\""                            { yybegin(IN_STRING_DQ); return AngelscriptTypes.STRING_LITERAL; }
	"'"                              { yybegin(IN_STRING_SQ); return AngelscriptTypes.STRING_LITERAL; }

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Keywords
	///////////////////////////////////////////////////////////////////////////////////////////////////

//	"class"|"struct"|"mixin"|"event"|"typedef"|"namespace"|"delegate"|
//	"UFUNCTION"|"UPROPERTY"|"UCLASS"|"USTRUCT"|
//	"shared"|"external"|"private"|"protected"|"const"|"auto"|
//	"override"|"final"|"explicit"|"property"|"in"|"out"|"inout"|
//	"if"|"else"|"switch"|"case"|"default"|"while"|"do"|"for"|"return"|
//	"break"|"continue"|"void"|"int"|"int8"|"int16"|"int32"|"int64"|
//	"uint"|"uint8"|"uint16"|"uint32"|"uint64"|"float"|"double"|"bool"|
//	"true"|"false"|"TRUE"|"FALSE"|"NULL"|"nullptr"|"not"|"and"|"or"|"xor"|"is" {
//																	 return AngelscriptTypes.KEYWORD;
//																 }

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Literals and identifiers
	///////////////////////////////////////////////////////////////////////////////////////////////////

	{FLOAT_NUMBER}                   { return AngelscriptTypes.NUMBER_LITERAL; }
	{HEX_INTEGER}|{BIN_INTEGER}|{DEC_INTEGER}
																	 { return AngelscriptTypes.NUMBER_LITERAL; }

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Punctuation and operators
	///////////////////////////////////////////////////////////////////////////////////////////////////
	

      "{"                             { return AngelscriptTypes.LBRACE; }
      "}"                             { return AngelscriptTypes.RBRACE; }
      "["                             { return AngelscriptTypes.LBRACK; }
      "]"                             { return AngelscriptTypes.RBRACK; }
      "("                             { return AngelscriptTypes.LPAREN; }
      ")"                             { return AngelscriptTypes.RPAREN; }
      "::"                            { return AngelscriptTypes.COLONCOLON; }
      ":"                             { return AngelscriptTypes.COLON; }
      ";"                             { return AngelscriptTypes.SEMICOLON; }
      ","                             { return AngelscriptTypes.COMMA; }
      "."                             { return AngelscriptTypes.DOT; }
      "="                             { return AngelscriptTypes.EQ; }
      "!="                            { return AngelscriptTypes.EXCLEQ; }
      "=="                            { return AngelscriptTypes.EQEQ; }
      "!"                             { return AngelscriptTypes.EXCL; }
      "+="                            { return AngelscriptTypes.PLUSEQ; }
      "+"                             { return AngelscriptTypes.PLUS; }
      "-="                            { return AngelscriptTypes.MINUSEQ; }
      "-"                             { return AngelscriptTypes.MINUS; }
      "#"                             { return AngelscriptTypes.SHA; }
      "|="                            { return AngelscriptTypes.OREQ; }
      "&="                            { return AngelscriptTypes.ANDEQ; }
      "&"                             { return AngelscriptTypes.AND; }
      "|"                             { return AngelscriptTypes.OR; }
      "<"                             { return AngelscriptTypes.LT; }
      "^="                            { return AngelscriptTypes.XOREQ; }
      "^"                             { return AngelscriptTypes.XOR; }
      "*="                            { return AngelscriptTypes.MULEQ; }
      "*"                             { return AngelscriptTypes.MUL; }
      "/="                            { return AngelscriptTypes.DIVEQ; }
      "/"                             { return AngelscriptTypes.DIV; }
      "%="                            { return AngelscriptTypes.REMEQ; }
      "%"                             { return AngelscriptTypes.REM; }
      ">"                             { return AngelscriptTypes.GT; }

      "true"|"false"                  { return AngelscriptTypes.BOOL_LITERAL; }
      "break"                         { return AngelscriptTypes.BREAK; }
      "const"                         { return AngelscriptTypes.CONST; }
      "continue"                      { return AngelscriptTypes.CONTINUE; }
      "else"                          { return AngelscriptTypes.ELSE; }
      "enum"                          { return AngelscriptTypes.ENUM; }
      "for"                           { return AngelscriptTypes.FOR; }
      "if"                            { return AngelscriptTypes.IF; }
      "OUT"                           { return AngelscriptTypes.OUT; }
      "in"                            { return AngelscriptTypes.IN; }
      "AUTO"                          { return AngelscriptTypes.AUTO; }
      "while"                         { return AngelscriptTypes.WHILE; }
      "public"                        { return AngelscriptTypes.PUBLIC; }
      "return"                        { return AngelscriptTypes.RETURN; }
      "static"                        { return AngelscriptTypes.STATIC; }
      "struct"                        { return AngelscriptTypes.STRUCT; }
      "class"                        { return AngelscriptTypes.CLASS; }
      "super"                         { return AngelscriptTypes.SUPER; }

    {IDENTIFIER}                     { return AngelscriptTypes.IDENTIFIER; }

//	"::"|"=="|"!="|">="|"<="|"<<="|">>="|">>>="|"<<"|">>"|">>>"|"&&"|"||"|
//	"++"|"--"|"**="|"**"|"+="|"-="|"*="|"/="|"%="|"&="|"|="|"^="|"!is"|
//	"@"|"~"|"?"|":"|"="|"+"|"-"|"*"|"/"|"%"|"&"|"|"|"^"|"!"|"<"|">"|
//	"."|","|";"|"("|")"|"{"|"}"|"["|"]" {
//																	 return AngelscriptTypes.OPERATOR;
//																 }
}

//<IN_BLOCK_COMMENT> {
//	"*/"                             { yybegin(YYINITIAL); return AngelscriptTypes.COMMENT; }
//	[^]                               { return AngelscriptTypes.COMMENT; }
//	<<EOF>>                          { yybegin(YYINITIAL); return AngelscriptTypes.COMMENT; }
//}

<IN_STRING_DQ> {
	{ESCAPE_SEQUENCE}                 { return AngelscriptTypes.STRING_LITERAL; }
	"\\\r?\n"                       { return AngelscriptTypes.STRING_LITERAL; }
	"\""                            { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
	[^\"\r\n]+                     { return AngelscriptTypes.STRING_LITERAL; }
	[^]                               { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
	<<EOF>>                          { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
}

<IN_STRING_SQ> {
	{ESCAPE_SEQUENCE}                 { return AngelscriptTypes.STRING_LITERAL; }
	"\\\r?\n"                       { return AngelscriptTypes.STRING_LITERAL; }
	"'"                              { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
	[^\\'\r\n]+                     { return AngelscriptTypes.STRING_LITERAL; }
	[^]                               { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
	<<EOF>>                          { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
}

<IN_RAW_STRING> {
	"\"\"\""                        { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
	[^]                               { return AngelscriptTypes.STRING_LITERAL; }
	<<EOF>>                          { yybegin(YYINITIAL); return AngelscriptTypes.STRING_LITERAL; }
}

[^]                                                         { return TokenType.BAD_CHARACTER; }
