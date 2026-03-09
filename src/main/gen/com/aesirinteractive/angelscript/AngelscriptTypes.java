// This is a generated file. Not intended for manual editing.
package com.aesirinteractive.angelscript;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.aesirinteractive.angelscript.psi.impl.*;

public interface AngelscriptTypes {

  IElementType ADD_EXPR = new AngelscriptElementType("ADD_EXPR");
  IElementType ARGUMENT = new AngelscriptElementType("ARGUMENT");
  IElementType ARG_LIST = new AngelscriptElementType("ARG_LIST");
  IElementType ASSIGNMENT_EXPR = new AngelscriptElementType("ASSIGNMENT_EXPR");
  IElementType BITWISE_AND_EXPR = new AngelscriptElementType("BITWISE_AND_EXPR");
  IElementType BREAK_STATEMENT = new AngelscriptElementType("BREAK_STATEMENT");
  IElementType CASE_STATEMENT = new AngelscriptElementType("CASE_STATEMENT");
  IElementType CAST_EXPR = new AngelscriptElementType("CAST_EXPR");
  IElementType CLASS_DECL = new AngelscriptElementType("CLASS_DECL");
  IElementType COMMA_EXPR = new AngelscriptElementType("COMMA_EXPR");
  IElementType COMMENT = new AngelscriptElementType("COMMENT");
  IElementType COMPOUND_STATEMENT = new AngelscriptElementType("COMPOUND_STATEMENT");
  IElementType CONDITIONAL_EXPR = new AngelscriptElementType("CONDITIONAL_EXPR");
  IElementType CONTINUE_STATEMENT = new AngelscriptElementType("CONTINUE_STATEMENT");
  IElementType DELEGATE_DECL = new AngelscriptElementType("DELEGATE_DECL");
  IElementType DO_STATEMENT = new AngelscriptElementType("DO_STATEMENT");
  IElementType ELSE_CLAUSE = new AngelscriptElementType("ELSE_CLAUSE");
  IElementType EQUALITY_EXPR = new AngelscriptElementType("EQUALITY_EXPR");
  IElementType EVENT_DECL = new AngelscriptElementType("EVENT_DECL");
  IElementType EXCLUSIVE_OR_EXPR = new AngelscriptElementType("EXCLUSIVE_OR_EXPR");
  IElementType EXPR = new AngelscriptElementType("EXPR");
  IElementType EXPRESSION_STATEMENT = new AngelscriptElementType("EXPRESSION_STATEMENT");
  IElementType FOREACH_STATEMENT = new AngelscriptElementType("FOREACH_STATEMENT");
  IElementType FOR_STATEMENT = new AngelscriptElementType("FOR_STATEMENT");
  IElementType FUNCTION_DECL = new AngelscriptElementType("FUNCTION_DECL");
  IElementType IF_STATEMENT = new AngelscriptElementType("IF_STATEMENT");
  IElementType INCLUSIVE_OR_EXPR = new AngelscriptElementType("INCLUSIVE_OR_EXPR");
  IElementType INIT_LIST_EXPR = new AngelscriptElementType("INIT_LIST_EXPR");
  IElementType LOGICAL_AND_EXPR = new AngelscriptElementType("LOGICAL_AND_EXPR");
  IElementType LOGICAL_OR_EXPR = new AngelscriptElementType("LOGICAL_OR_EXPR");
  IElementType MIXIN_DECL = new AngelscriptElementType("MIXIN_DECL");
  IElementType MULTIPLY_EXPR = new AngelscriptElementType("MULTIPLY_EXPR");
  IElementType PARAMETER_DECL = new AngelscriptElementType("PARAMETER_DECL");
  IElementType PARAMETER_LIST = new AngelscriptElementType("PARAMETER_LIST");
  IElementType PARENTHESIZED_EXPR = new AngelscriptElementType("PARENTHESIZED_EXPR");
  IElementType POSTFIX_EXPR = new AngelscriptElementType("POSTFIX_EXPR");
  IElementType PRIMARY_EXPR = new AngelscriptElementType("PRIMARY_EXPR");
  IElementType RELATIONAL_EXPR = new AngelscriptElementType("RELATIONAL_EXPR");
  IElementType RETURN_STATEMENT = new AngelscriptElementType("RETURN_STATEMENT");
  IElementType SCOPE_REF = new AngelscriptElementType("SCOPE_REF");
  IElementType SHIFT_EXPR = new AngelscriptElementType("SHIFT_EXPR");
  IElementType STATEMENT = new AngelscriptElementType("STATEMENT");
  IElementType STRING_CONCAT_EXPR = new AngelscriptElementType("STRING_CONCAT_EXPR");
  IElementType STRUCT_DECL = new AngelscriptElementType("STRUCT_DECL");
  IElementType SWITCH_STATEMENT = new AngelscriptElementType("SWITCH_STATEMENT");
  IElementType TYPEDEF_DECL = new AngelscriptElementType("TYPEDEF_DECL");
  IElementType TYPE_ARGUMENT = new AngelscriptElementType("TYPE_ARGUMENT");
  IElementType TYPE_REF = new AngelscriptElementType("TYPE_REF");
  IElementType UNARY_EXPR = new AngelscriptElementType("UNARY_EXPR");
  IElementType U_CLASS_DECL = new AngelscriptElementType("U_CLASS_DECL");
  IElementType U_FUNCTION_DECL = new AngelscriptElementType("U_FUNCTION_DECL");
  IElementType U_PROPERTY_DECL = new AngelscriptElementType("U_PROPERTY_DECL");
  IElementType U_STRUCT_DECL = new AngelscriptElementType("U_STRUCT_DECL");
  IElementType VARIABLE_ACCESS_EXPR = new AngelscriptElementType("VARIABLE_ACCESS_EXPR");
  IElementType VARIABLE_DECL = new AngelscriptElementType("VARIABLE_DECL");
  IElementType WHILE_STATEMENT = new AngelscriptElementType("WHILE_STATEMENT");

  IElementType AND = new AngelscriptTokenType("&");
  IElementType ANDEQ = new AngelscriptTokenType("&=");
  IElementType AUTO = new AngelscriptTokenType("AUTO");
  IElementType BOOL_LITERAL = new AngelscriptTokenType("BOOL_LITERAL");
  IElementType BREAK = new AngelscriptTokenType("break");
  IElementType CLASS = new AngelscriptTokenType("class");
  IElementType COLON = new AngelscriptTokenType(":");
  IElementType COLONCOLON = new AngelscriptTokenType("::");
  IElementType COMMA = new AngelscriptTokenType(",");
  IElementType CONST = new AngelscriptTokenType("const");
  IElementType CONTINUE = new AngelscriptTokenType("continue");
  IElementType DIV = new AngelscriptTokenType("/");
  IElementType DIVEQ = new AngelscriptTokenType("/=");
  IElementType DOT = new AngelscriptTokenType(".");
  IElementType ELSE = new AngelscriptTokenType("else");
  IElementType ENUM = new AngelscriptTokenType("enum");
  IElementType EQ = new AngelscriptTokenType("=");
  IElementType EQEQ = new AngelscriptTokenType("==");
  IElementType EXCL = new AngelscriptTokenType("!");
  IElementType EXCLEQ = new AngelscriptTokenType("!=");
  IElementType FOR = new AngelscriptTokenType("for");
  IElementType GT = new AngelscriptTokenType(">");
  IElementType IDENTIFIER = new AngelscriptTokenType("IDENTIFIER");
  IElementType IF = new AngelscriptTokenType("if");
  IElementType IN = new AngelscriptTokenType("in");
  IElementType LBRACE = new AngelscriptTokenType("{");
  IElementType LBRACK = new AngelscriptTokenType("[");
  IElementType LPAREN = new AngelscriptTokenType("(");
  IElementType LT = new AngelscriptTokenType("<");
  IElementType MINUS = new AngelscriptTokenType("-");
  IElementType MINUSEQ = new AngelscriptTokenType("-=");
  IElementType MUL = new AngelscriptTokenType("*");
  IElementType MULEQ = new AngelscriptTokenType("*=");
  IElementType NUMBER_LITERAL = new AngelscriptTokenType("NUMBER_LITERAL");
  IElementType OR = new AngelscriptTokenType("|");
  IElementType OREQ = new AngelscriptTokenType("|=");
  IElementType OUT = new AngelscriptTokenType("OUT");
  IElementType PLUS = new AngelscriptTokenType("+");
  IElementType PLUSEQ = new AngelscriptTokenType("+=");
  IElementType PUBLIC = new AngelscriptTokenType("public");
  IElementType RBRACE = new AngelscriptTokenType("}");
  IElementType RBRACK = new AngelscriptTokenType("]");
  IElementType REM = new AngelscriptTokenType("%");
  IElementType REMEQ = new AngelscriptTokenType("%=");
  IElementType RETURN = new AngelscriptTokenType("return");
  IElementType RPAREN = new AngelscriptTokenType(")");
  IElementType SEMICOLON = new AngelscriptTokenType(";");
  IElementType SHA = new AngelscriptTokenType("#");
  IElementType STATIC = new AngelscriptTokenType("static");
  IElementType STRING_LITERAL = new AngelscriptTokenType("STRING_LITERAL");
  IElementType STRUCT = new AngelscriptTokenType("struct");
  IElementType SUPER = new AngelscriptTokenType("super");
  IElementType WHILE = new AngelscriptTokenType("while");
  IElementType XOR = new AngelscriptTokenType("^");
  IElementType XOREQ = new AngelscriptTokenType("^=");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ADD_EXPR) {
        return new AngelscriptAddExprImpl(node);
      }
      else if (type == ARGUMENT) {
        return new AngelscriptArgumentImpl(node);
      }
      else if (type == ARG_LIST) {
        return new AngelscriptArgListImpl(node);
      }
      else if (type == ASSIGNMENT_EXPR) {
        return new AngelscriptAssignmentExprImpl(node);
      }
      else if (type == BITWISE_AND_EXPR) {
        return new AngelscriptBitwiseAndExprImpl(node);
      }
      else if (type == BREAK_STATEMENT) {
        return new AngelscriptBreakStatementImpl(node);
      }
      else if (type == CASE_STATEMENT) {
        return new AngelscriptCaseStatementImpl(node);
      }
      else if (type == CAST_EXPR) {
        return new AngelscriptCastExprImpl(node);
      }
      else if (type == CLASS_DECL) {
        return new AngelscriptClassDeclImpl(node);
      }
      else if (type == COMMA_EXPR) {
        return new AngelscriptCommaExprImpl(node);
      }
      else if (type == COMMENT) {
        return new AngelscriptCommentImpl(node);
      }
      else if (type == COMPOUND_STATEMENT) {
        return new AngelscriptCompoundStatementImpl(node);
      }
      else if (type == CONDITIONAL_EXPR) {
        return new AngelscriptConditionalExprImpl(node);
      }
      else if (type == CONTINUE_STATEMENT) {
        return new AngelscriptContinueStatementImpl(node);
      }
      else if (type == DELEGATE_DECL) {
        return new AngelscriptDelegateDeclImpl(node);
      }
      else if (type == DO_STATEMENT) {
        return new AngelscriptDoStatementImpl(node);
      }
      else if (type == ELSE_CLAUSE) {
        return new AngelscriptElseClauseImpl(node);
      }
      else if (type == EQUALITY_EXPR) {
        return new AngelscriptEqualityExprImpl(node);
      }
      else if (type == EVENT_DECL) {
        return new AngelscriptEventDeclImpl(node);
      }
      else if (type == EXCLUSIVE_OR_EXPR) {
        return new AngelscriptExclusiveOrExprImpl(node);
      }
      else if (type == EXPRESSION_STATEMENT) {
        return new AngelscriptExpressionStatementImpl(node);
      }
      else if (type == FOREACH_STATEMENT) {
        return new AngelscriptForeachStatementImpl(node);
      }
      else if (type == FOR_STATEMENT) {
        return new AngelscriptForStatementImpl(node);
      }
      else if (type == FUNCTION_DECL) {
        return new AngelscriptFunctionDeclImpl(node);
      }
      else if (type == IF_STATEMENT) {
        return new AngelscriptIfStatementImpl(node);
      }
      else if (type == INCLUSIVE_OR_EXPR) {
        return new AngelscriptInclusiveOrExprImpl(node);
      }
      else if (type == INIT_LIST_EXPR) {
        return new AngelscriptInitListExprImpl(node);
      }
      else if (type == LOGICAL_AND_EXPR) {
        return new AngelscriptLogicalAndExprImpl(node);
      }
      else if (type == LOGICAL_OR_EXPR) {
        return new AngelscriptLogicalOrExprImpl(node);
      }
      else if (type == MIXIN_DECL) {
        return new AngelscriptMixinDeclImpl(node);
      }
      else if (type == MULTIPLY_EXPR) {
        return new AngelscriptMultiplyExprImpl(node);
      }
      else if (type == PARAMETER_DECL) {
        return new AngelscriptParameterDeclImpl(node);
      }
      else if (type == PARAMETER_LIST) {
        return new AngelscriptParameterListImpl(node);
      }
      else if (type == PARENTHESIZED_EXPR) {
        return new AngelscriptParenthesizedExprImpl(node);
      }
      else if (type == POSTFIX_EXPR) {
        return new AngelscriptPostfixExprImpl(node);
      }
      else if (type == PRIMARY_EXPR) {
        return new AngelscriptPrimaryExprImpl(node);
      }
      else if (type == RELATIONAL_EXPR) {
        return new AngelscriptRelationalExprImpl(node);
      }
      else if (type == RETURN_STATEMENT) {
        return new AngelscriptReturnStatementImpl(node);
      }
      else if (type == SCOPE_REF) {
        return new AngelscriptScopeRefImpl(node);
      }
      else if (type == SHIFT_EXPR) {
        return new AngelscriptShiftExprImpl(node);
      }
      else if (type == STATEMENT) {
        return new AngelscriptStatementImpl(node);
      }
      else if (type == STRING_CONCAT_EXPR) {
        return new AngelscriptStringConcatExprImpl(node);
      }
      else if (type == STRUCT_DECL) {
        return new AngelscriptStructDeclImpl(node);
      }
      else if (type == SWITCH_STATEMENT) {
        return new AngelscriptSwitchStatementImpl(node);
      }
      else if (type == TYPEDEF_DECL) {
        return new AngelscriptTypedefDeclImpl(node);
      }
      else if (type == TYPE_ARGUMENT) {
        return new AngelscriptTypeArgumentImpl(node);
      }
      else if (type == TYPE_REF) {
        return new AngelscriptTypeRefImpl(node);
      }
      else if (type == UNARY_EXPR) {
        return new AngelscriptUnaryExprImpl(node);
      }
      else if (type == U_CLASS_DECL) {
        return new AngelscriptUClassDeclImpl(node);
      }
      else if (type == U_FUNCTION_DECL) {
        return new AngelscriptUFunctionDeclImpl(node);
      }
      else if (type == U_PROPERTY_DECL) {
        return new AngelscriptUPropertyDeclImpl(node);
      }
      else if (type == U_STRUCT_DECL) {
        return new AngelscriptUStructDeclImpl(node);
      }
      else if (type == VARIABLE_ACCESS_EXPR) {
        return new AngelscriptVariableAccessExprImpl(node);
      }
      else if (type == VARIABLE_DECL) {
        return new AngelscriptVariableDeclImpl(node);
      }
      else if (type == WHILE_STATEMENT) {
        return new AngelscriptWhileStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
