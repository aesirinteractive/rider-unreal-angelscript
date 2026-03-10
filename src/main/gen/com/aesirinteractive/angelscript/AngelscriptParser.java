// This is a generated file. Not intended for manual editing.
package com.aesirinteractive.angelscript;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.aesirinteractive.angelscript.AngelscriptTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class AngelscriptParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return Script(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ADD_EXPR, ASSIGNMENT_EXPR, BITWISE_AND_EXPR, CAST_EXPR,
      COMMA_EXPR, CONDITIONAL_EXPR, EQUALITY_EXPR, EXCLUSIVE_OR_EXPR,
      EXPR, INCLUSIVE_OR_EXPR, INIT_LIST_EXPR, LOGICAL_AND_EXPR,
      LOGICAL_OR_EXPR, MULTIPLY_EXPR, PARENTHESIZED_EXPR, POSTFIX_EXPR,
      PRIMARY_EXPR, RELATIONAL_EXPR, SHIFT_EXPR, STRING_CONCAT_EXPR,
      UNARY_EXPR, VARIABLE_ACCESS_EXPR),
  };

  /* ********************************************************** */
  // PRIVATE_KW | PROTECTED_KW
  static boolean AccessSpecifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessSpecifier")) return false;
    if (!nextTokenIs(b, "", PRIVATE_KW, PROTECTED_KW)) return false;
    boolean r;
    r = consumeToken(b, PRIVATE_KW);
    if (!r) r = consumeToken(b, PROTECTED_KW);
    return r;
  }

  /* ********************************************************** */
  // MultiplyExpr ((PLUS | MINUS) MultiplyExpr)*
  public static boolean AddExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AddExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, ADD_EXPR, "<add expr>");
    r = MultiplyExpr(b, l + 1);
    r = r && AddExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((PLUS | MINUS) MultiplyExpr)*
  private static boolean AddExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AddExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!AddExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "AddExpr_1", c)) break;
    }
    return true;
  }

  // (PLUS | MINUS) MultiplyExpr
  private static boolean AddExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AddExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = AddExpr_1_0_0(b, l + 1);
    r = r && MultiplyExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PLUS | MINUS
  private static boolean AddExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AddExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    return r;
  }

  /* ********************************************************** */
  // LPAREN [ Argument (COMMA Argument)* ] RPAREN
  public static boolean ArgList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && ArgList_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, ARG_LIST, r);
    return r;
  }

  // [ Argument (COMMA Argument)* ]
  private static boolean ArgList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgList_1")) return false;
    ArgList_1_0(b, l + 1);
    return true;
  }

  // Argument (COMMA Argument)*
  private static boolean ArgList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Argument(b, l + 1);
    r = r && ArgList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA Argument)*
  private static boolean ArgList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgList_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ArgList_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ArgList_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA Argument
  private static boolean ArgList_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgList_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && Argument(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [ identifier COLON ] Expr
  public static boolean Argument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Argument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT, "<argument>");
    r = Argument_0(b, l + 1);
    r = r && Expr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [ identifier COLON ]
  private static boolean Argument_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Argument_0")) return false;
    Argument_0_0(b, l + 1);
    return true;
  }

  // identifier COLON
  private static boolean Argument_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Argument_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && consumeToken(b, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ConditionalExpr (AssignmentOp AssignmentExpr)?
  public static boolean AssignmentExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignmentExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, ASSIGNMENT_EXPR, "<assignment expr>");
    r = ConditionalExpr(b, l + 1);
    r = r && AssignmentExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (AssignmentOp AssignmentExpr)?
  private static boolean AssignmentExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignmentExpr_1")) return false;
    AssignmentExpr_1_0(b, l + 1);
    return true;
  }

  // AssignmentOp AssignmentExpr
  private static boolean AssignmentExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignmentExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = AssignmentOp(b, l + 1);
    r = r && AssignmentExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EQ | MULEQ | POWEREQ | DIVEQ | REMEQ | PLUSEQ | MINUSEQ
  //                        | LSHIFTEQ | RSHIFTEQ | RRSHIFTEQ | ANDEQ | XOREQ | OREQ
  static boolean AssignmentOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignmentOp")) return false;
    boolean r;
    r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, MULEQ);
    if (!r) r = consumeToken(b, POWEREQ);
    if (!r) r = consumeToken(b, DIVEQ);
    if (!r) r = consumeToken(b, REMEQ);
    if (!r) r = consumeToken(b, PLUSEQ);
    if (!r) r = consumeToken(b, MINUSEQ);
    if (!r) r = consumeToken(b, LSHIFTEQ);
    if (!r) r = consumeToken(b, RSHIFTEQ);
    if (!r) r = consumeToken(b, RRSHIFTEQ);
    if (!r) r = consumeToken(b, ANDEQ);
    if (!r) r = consumeToken(b, XOREQ);
    if (!r) r = consumeToken(b, OREQ);
    return r;
  }

  /* ********************************************************** */
  // EqualityExpr (AND EqualityExpr)*
  public static boolean BitwiseAndExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BitwiseAndExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, BITWISE_AND_EXPR, "<bitwise and expr>");
    r = EqualityExpr(b, l + 1);
    r = r && BitwiseAndExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (AND EqualityExpr)*
  private static boolean BitwiseAndExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BitwiseAndExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!BitwiseAndExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "BitwiseAndExpr_1", c)) break;
    }
    return true;
  }

  // AND EqualityExpr
  private static boolean BitwiseAndExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BitwiseAndExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AND);
    r = r && EqualityExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IfDefBlock | Declaration | Statement | Comment
  static boolean BlockItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem")) return false;
    boolean r;
    r = IfDefBlock(b, l + 1);
    if (!r) r = Declaration(b, l + 1);
    if (!r) r = Statement(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | IF | WHILE | FOR | RETURN | BREAK | CONTINUE
  //                                | SWITCH_KW | DO_KW | CASE_KW | DEFAULT_KW | LBRACE
  //                                | CONST | VOID_KW
  //                                | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                | PLUSPLUS | MINUSMINUS | MINUS | EXCL | AT | TILDE | LPAREN
  //                                | NUMBER_LITERAL | STRING_LITERAL | BOOL_LITERAL | NULLPTR_KW
  //                                | IDENTIFIER | COMMENT
  //                                | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>)
  static boolean BlockItem_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !BlockItem_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | IF | WHILE | FOR | RETURN | BREAK | CONTINUE
  //                                | SWITCH_KW | DO_KW | CASE_KW | DEFAULT_KW | LBRACE
  //                                | CONST | VOID_KW
  //                                | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                | PLUSPLUS | MINUSMINUS | MINUS | EXCL | AT | TILDE | LPAREN
  //                                | NUMBER_LITERAL | STRING_LITERAL | BOOL_LITERAL | NULLPTR_KW
  //                                | IDENTIFIER | COMMENT
  //                                | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>
  private static boolean BlockItem_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, WHILE);
    if (!r) r = consumeToken(b, FOR);
    if (!r) r = consumeToken(b, RETURN);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = consumeToken(b, CONTINUE);
    if (!r) r = consumeToken(b, SWITCH_KW);
    if (!r) r = consumeToken(b, DO_KW);
    if (!r) r = consumeToken(b, CASE_KW);
    if (!r) r = consumeToken(b, DEFAULT_KW);
    if (!r) r = consumeToken(b, LBRACE);
    if (!r) r = consumeToken(b, CONST);
    if (!r) r = consumeToken(b, VOID_KW);
    if (!r) r = consumeToken(b, INT_KW);
    if (!r) r = consumeToken(b, INT8_KW);
    if (!r) r = consumeToken(b, INT16_KW);
    if (!r) r = consumeToken(b, INT32_KW);
    if (!r) r = consumeToken(b, INT64_KW);
    if (!r) r = consumeToken(b, UINT_KW);
    if (!r) r = consumeToken(b, UINT8_KW);
    if (!r) r = consumeToken(b, UINT16_KW);
    if (!r) r = consumeToken(b, UINT32_KW);
    if (!r) r = consumeToken(b, UINT64_KW);
    if (!r) r = consumeToken(b, FLOAT_KW);
    if (!r) r = consumeToken(b, DOUBLE_KW);
    if (!r) r = consumeToken(b, BOOL_KW);
    if (!r) r = consumeToken(b, AUTO_KW);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, PLUSPLUS);
    if (!r) r = consumeToken(b, MINUSMINUS);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, EXCL);
    if (!r) r = consumeToken(b, AT);
    if (!r) r = consumeToken(b, TILDE);
    if (!r) r = consumeToken(b, LPAREN);
    if (!r) r = consumeToken(b, NUMBER_LITERAL);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    if (!r) r = consumeToken(b, BOOL_LITERAL);
    if (!r) r = consumeToken(b, NULLPTR_KW);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, PP_IF);
    if (!r) r = consumeToken(b, PP_ELIF);
    if (!r) r = consumeToken(b, PP_ELSE);
    if (!r) r = consumeToken(b, PP_ENDIF);
    if (!r) r = consumeToken(b, PP_DEFINE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | <<eof>>) BlockItem
  static boolean BlockItem_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = BlockItem_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && BlockItem(b, l + 1);
    exit_section_(b, l, m, r, p, AngelscriptParser::BlockItem_recover);
    return r || p;
  }

  // !(RBRACE | <<eof>>)
  private static boolean BlockItem_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !BlockItem_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | <<eof>>
  private static boolean BlockItem_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // BREAK    SEMICOLON
  public static boolean BreakStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BreakStatement")) return false;
    if (!nextTokenIs(b, BREAK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, BREAK, SEMICOLON);
    exit_section_(b, m, BREAK_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // TypeArgument? ArgList
  static boolean CallSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CallSuffix")) return false;
    if (!nextTokenIs(b, "", LPAREN, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = CallSuffix_0(b, l + 1);
    r = r && ArgList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TypeArgument?
  private static boolean CallSuffix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CallSuffix_0")) return false;
    TypeArgument(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ( CASE_KW Expr | DEFAULT_KW ) COLON (NonCaseStatement | VariableDecl)*
  public static boolean CaseStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement")) return false;
    if (!nextTokenIs(b, "<case statement>", CASE_KW, DEFAULT_KW)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CASE_STATEMENT, "<case statement>");
    r = CaseStatement_0(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && CaseStatement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // CASE_KW Expr | DEFAULT_KW
  private static boolean CaseStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = CaseStatement_0_0(b, l + 1);
    if (!r) r = consumeToken(b, DEFAULT_KW);
    exit_section_(b, m, null, r);
    return r;
  }

  // CASE_KW Expr
  private static boolean CaseStatement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CASE_KW);
    r = r && Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (NonCaseStatement | VariableDecl)*
  private static boolean CaseStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!CaseStatement_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "CaseStatement_2", c)) break;
    }
    return true;
  }

  // NonCaseStatement | VariableDecl
  private static boolean CaseStatement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement_2_0")) return false;
    boolean r;
    r = NonCaseStatement(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LPAREN TypeRef RPAREN UnaryExpr
  //            | PrimitiveType LPAREN Expr RPAREN
  public static boolean CastExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CastExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CAST_EXPR, "<cast expr>");
    r = CastExpr_0(b, l + 1);
    if (!r) r = CastExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LPAREN TypeRef RPAREN UnaryExpr
  private static boolean CastExpr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CastExpr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && TypeRef(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && UnaryExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PrimitiveType LPAREN Expr RPAREN
  private static boolean CastExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CastExpr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrimitiveType(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && Expr(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // UClassDecl? CLASS identifier InheritanceList? LBRACE ClassMember_with_recover* RBRACE SEMICOLON?
  public static boolean ClassDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl")) return false;
    if (!nextTokenIs(b, "<class decl>", CLASS, UCLASS_KW)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CLASS_DECL, "<class decl>");
    r = ClassDecl_0(b, l + 1);
    r = r && consumeToken(b, CLASS);
    r = r && identifier(b, l + 1);
    r = r && ClassDecl_3(b, l + 1);
    r = r && consumeToken(b, LBRACE);
    r = r && ClassDecl_5(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    r = r && ClassDecl_7(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // UClassDecl?
  private static boolean ClassDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl_0")) return false;
    UClassDecl(b, l + 1);
    return true;
  }

  // InheritanceList?
  private static boolean ClassDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl_3")) return false;
    InheritanceList(b, l + 1);
    return true;
  }

  // ClassMember_with_recover*
  private static boolean ClassDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ClassMember_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ClassDecl_5", c)) break;
    }
    return true;
  }

  // SEMICOLON?
  private static boolean ClassDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl_7")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // IfDefBlock | ConstructorDecl | FunctionDecl | DefaultValueDecl | VariableDecl | Comment
  static boolean ClassMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember")) return false;
    boolean r;
    r = IfDefBlock(b, l + 1);
    if (!r) r = ConstructorDecl(b, l + 1);
    if (!r) r = FunctionDecl(b, l + 1);
    if (!r) r = DefaultValueDecl(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | UFUNCTION_KW | UPROPERTY_KW | PRIVATE_KW | PROTECTED_KW
  //                                 | STATIC | CONST | SHARED_KW | EXTERNAL_KW | DEFAULT_KW | VOID_KW
  //                                 | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                 | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                 | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                 | TILDE | IDENTIFIER | COMMENT
  //                                 | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>)
  static boolean ClassMember_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ClassMember_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | UFUNCTION_KW | UPROPERTY_KW | PRIVATE_KW | PROTECTED_KW
  //                                 | STATIC | CONST | SHARED_KW | EXTERNAL_KW | DEFAULT_KW | VOID_KW
  //                                 | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                 | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                 | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                 | TILDE | IDENTIFIER | COMMENT
  //                                 | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>
  private static boolean ClassMember_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, UFUNCTION_KW);
    if (!r) r = consumeToken(b, UPROPERTY_KW);
    if (!r) r = consumeToken(b, PRIVATE_KW);
    if (!r) r = consumeToken(b, PROTECTED_KW);
    if (!r) r = consumeToken(b, STATIC);
    if (!r) r = consumeToken(b, CONST);
    if (!r) r = consumeToken(b, SHARED_KW);
    if (!r) r = consumeToken(b, EXTERNAL_KW);
    if (!r) r = consumeToken(b, DEFAULT_KW);
    if (!r) r = consumeToken(b, VOID_KW);
    if (!r) r = consumeToken(b, INT_KW);
    if (!r) r = consumeToken(b, INT8_KW);
    if (!r) r = consumeToken(b, INT16_KW);
    if (!r) r = consumeToken(b, INT32_KW);
    if (!r) r = consumeToken(b, INT64_KW);
    if (!r) r = consumeToken(b, UINT_KW);
    if (!r) r = consumeToken(b, UINT8_KW);
    if (!r) r = consumeToken(b, UINT16_KW);
    if (!r) r = consumeToken(b, UINT32_KW);
    if (!r) r = consumeToken(b, UINT64_KW);
    if (!r) r = consumeToken(b, FLOAT_KW);
    if (!r) r = consumeToken(b, DOUBLE_KW);
    if (!r) r = consumeToken(b, BOOL_KW);
    if (!r) r = consumeToken(b, AUTO_KW);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, TILDE);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, PP_IF);
    if (!r) r = consumeToken(b, PP_ELIF);
    if (!r) r = consumeToken(b, PP_ELSE);
    if (!r) r = consumeToken(b, PP_ENDIF);
    if (!r) r = consumeToken(b, PP_DEFINE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | <<eof>>) ClassMember
  static boolean ClassMember_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = ClassMember_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && ClassMember(b, l + 1);
    exit_section_(b, l, m, r, p, AngelscriptParser::ClassMember_recover);
    return r || p;
  }

  // !(RBRACE | <<eof>>)
  private static boolean ClassMember_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ClassMember_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | <<eof>>
  private static boolean ClassMember_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // AssignmentExpr (COMMA AssignmentExpr)*
  public static boolean CommaExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CommaExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, COMMA_EXPR, "<comma expr>");
    r = AssignmentExpr(b, l + 1);
    r = r && CommaExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA AssignmentExpr)*
  private static boolean CommaExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CommaExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!CommaExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "CommaExpr_1", c)) break;
    }
    return true;
  }

  // COMMA AssignmentExpr
  private static boolean CommaExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CommaExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && AssignmentExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // COMMENT
  public static boolean Comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Comment")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    exit_section_(b, m, COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // LBRACE BlockItem_with_recover* RBRACE
  public static boolean CompoundStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CompoundStatement")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && CompoundStatement_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, COMPOUND_STATEMENT, r);
    return r;
  }

  // BlockItem_with_recover*
  private static boolean CompoundStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CompoundStatement_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!BlockItem_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "CompoundStatement_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LogicalOrExpr (QUEST Expr? COLON Expr)?
  public static boolean ConditionalExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, CONDITIONAL_EXPR, "<conditional expr>");
    r = LogicalOrExpr(b, l + 1);
    r = r && ConditionalExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (QUEST Expr? COLON Expr)?
  private static boolean ConditionalExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr_1")) return false;
    ConditionalExpr_1_0(b, l + 1);
    return true;
  }

  // QUEST Expr? COLON Expr
  private static boolean ConditionalExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QUEST);
    r = r && ConditionalExpr_1_0_1(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Expr?
  private static boolean ConditionalExpr_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr_1_0_1")) return false;
    Expr(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // AccessSpecifier?
  //                     identifier
  //                     ParameterList
  //                     (SEMICOLON | CompoundStatement SEMICOLON?)
  public static boolean ConstructorDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstructorDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONSTRUCTOR_DECL, "<constructor decl>");
    r = ConstructorDecl_0(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && ParameterList(b, l + 1);
    r = r && ConstructorDecl_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // AccessSpecifier?
  private static boolean ConstructorDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstructorDecl_0")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // SEMICOLON | CompoundStatement SEMICOLON?
  private static boolean ConstructorDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstructorDecl_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    if (!r) r = ConstructorDecl_3_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CompoundStatement SEMICOLON?
  private static boolean ConstructorDecl_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstructorDecl_3_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = CompoundStatement(b, l + 1);
    r = r && ConstructorDecl_3_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SEMICOLON?
  private static boolean ConstructorDecl_3_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstructorDecl_3_1_1")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // CONTINUE SEMICOLON
  public static boolean ContinueStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ContinueStatement")) return false;
    if (!nextTokenIs(b, CONTINUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONTINUE, SEMICOLON);
    exit_section_(b, m, CONTINUE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // PrimitiveType | QUEST | AUTO_KW | AUTO | identifier
  static boolean DataType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DataType")) return false;
    boolean r;
    r = PrimitiveType(b, l + 1);
    if (!r) r = consumeToken(b, QUEST);
    if (!r) r = consumeToken(b, AUTO_KW);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = identifier(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // NamespaceDecl
  //                       | EnumDecl
  //                       | TypedefDecl
  //                       | ClassDecl
  //                       | StructDecl
  //                       | EventDecl
  //                       | DelegateDecl
  //                       | MixinDecl
  //                       | FunctionDecl
  //                       | DefaultValueDecl
  //                       | VariableDecl
  //                       | Comment
  static boolean Declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Declaration")) return false;
    boolean r;
    r = NamespaceDecl(b, l + 1);
    if (!r) r = EnumDecl(b, l + 1);
    if (!r) r = TypedefDecl(b, l + 1);
    if (!r) r = ClassDecl(b, l + 1);
    if (!r) r = StructDecl(b, l + 1);
    if (!r) r = EventDecl(b, l + 1);
    if (!r) r = DelegateDecl(b, l + 1);
    if (!r) r = MixinDecl(b, l + 1);
    if (!r) r = FunctionDecl(b, l + 1);
    if (!r) r = DefaultValueDecl(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // TypeRef TypeMod?
  static boolean DeclarationSpecifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DeclarationSpecifier")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeRef(b, l + 1);
    r = r && DeclarationSpecifier_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TypeMod?
  private static boolean DeclarationSpecifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DeclarationSpecifier_1")) return false;
    TypeMod(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // DEFAULT_KW Expr SEMICOLON
  public static boolean DefaultValueDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DefaultValueDecl")) return false;
    if (!nextTokenIs(b, DEFAULT_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEFAULT_KW);
    r = r && Expr(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, DEFAULT_VALUE_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // DELEGATE_KW FunctionDecl
  public static boolean DelegateDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DelegateDecl")) return false;
    if (!nextTokenIs(b, DELEGATE_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DELEGATE_KW);
    r = r && FunctionDecl(b, l + 1);
    exit_section_(b, m, DELEGATE_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // DO_KW Statement WHILE ParenthesizedExpr SEMICOLON
  public static boolean DoStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DoStatement")) return false;
    if (!nextTokenIs(b, DO_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DO_KW);
    r = r && Statement(b, l + 1);
    r = r && consumeToken(b, WHILE);
    r = r && ParenthesizedExpr(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, DO_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // ELSE Statement
  public static boolean ElseClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ElseClause")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && Statement(b, l + 1);
    exit_section_(b, m, ELSE_CLAUSE, r);
    return r;
  }

  /* ********************************************************** */
  // UEnumDecl? ENUM identifier LBRACE EnumVariant_with_recover* RBRACE SEMICOLON?
  public static boolean EnumDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDecl")) return false;
    if (!nextTokenIs(b, "<enum decl>", ENUM, UENUM_KW)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENUM_DECL, "<enum decl>");
    r = EnumDecl_0(b, l + 1);
    r = r && consumeToken(b, ENUM);
    p = r; // pin = 2
    r = r && report_error_(b, identifier(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LBRACE)) && r;
    r = p && report_error_(b, EnumDecl_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RBRACE)) && r;
    r = p && EnumDecl_6(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // UEnumDecl?
  private static boolean EnumDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDecl_0")) return false;
    UEnumDecl(b, l + 1);
    return true;
  }

  // EnumVariant_with_recover*
  private static boolean EnumDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDecl_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!EnumVariant_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "EnumDecl_4", c)) break;
    }
    return true;
  }

  // SEMICOLON?
  private static boolean EnumDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDecl_6")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // identifier (EQ Expr)? COMMA?
  public static boolean EnumVariant(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && EnumVariant_1(b, l + 1);
    r = r && EnumVariant_2(b, l + 1);
    exit_section_(b, m, ENUM_VARIANT, r);
    return r;
  }

  // (EQ Expr)?
  private static boolean EnumVariant_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_1")) return false;
    EnumVariant_1_0(b, l + 1);
    return true;
  }

  // EQ Expr
  private static boolean EnumVariant_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQ);
    r = r && Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA?
  private static boolean EnumVariant_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // !(RBRACE | IDENTIFIER | <<eof>>)
  static boolean EnumVariant_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !EnumVariant_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | IDENTIFIER | <<eof>>
  private static boolean EnumVariant_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | <<eof>>) EnumVariant
  static boolean EnumVariant_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = EnumVariant_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && EnumVariant(b, l + 1);
    exit_section_(b, l, m, r, p, AngelscriptParser::EnumVariant_recover);
    return r || p;
  }

  // !(RBRACE | <<eof>>)
  private static boolean EnumVariant_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !EnumVariant_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | <<eof>>
  private static boolean EnumVariant_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumVariant_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // RelationalExpr ((EQEQ | EXCLEQ) RelationalExpr)*
  public static boolean EqualityExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EqualityExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, EQUALITY_EXPR, "<equality expr>");
    r = RelationalExpr(b, l + 1);
    r = r && EqualityExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((EQEQ | EXCLEQ) RelationalExpr)*
  private static boolean EqualityExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EqualityExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!EqualityExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "EqualityExpr_1", c)) break;
    }
    return true;
  }

  // (EQEQ | EXCLEQ) RelationalExpr
  private static boolean EqualityExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EqualityExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EqualityExpr_1_0_0(b, l + 1);
    r = r && RelationalExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // EQEQ | EXCLEQ
  private static boolean EqualityExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EqualityExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, EQEQ);
    if (!r) r = consumeToken(b, EXCLEQ);
    return r;
  }

  /* ********************************************************** */
  // EVENT_KW    FunctionDecl
  public static boolean EventDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EventDecl")) return false;
    if (!nextTokenIs(b, EVENT_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EVENT_KW);
    r = r && FunctionDecl(b, l + 1);
    exit_section_(b, m, EVENT_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // BitwiseAndExpr ((XOR | XOR_KW) BitwiseAndExpr)*
  public static boolean ExclusiveOrExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, EXCLUSIVE_OR_EXPR, "<exclusive or expr>");
    r = BitwiseAndExpr(b, l + 1);
    r = r && ExclusiveOrExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((XOR | XOR_KW) BitwiseAndExpr)*
  private static boolean ExclusiveOrExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ExclusiveOrExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ExclusiveOrExpr_1", c)) break;
    }
    return true;
  }

  // (XOR | XOR_KW) BitwiseAndExpr
  private static boolean ExclusiveOrExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ExclusiveOrExpr_1_0_0(b, l + 1);
    r = r && BitwiseAndExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // XOR | XOR_KW
  private static boolean ExclusiveOrExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, XOR);
    if (!r) r = consumeToken(b, XOR_KW);
    return r;
  }

  /* ********************************************************** */
  // CommaExpr
  public static boolean Expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, EXPR, "<expr>");
    r = CommaExpr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Expr SEMICOLON
  public static boolean ExpressionStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpressionStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION_STATEMENT, "<expression statement>");
    r = Expr(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DOT identifier
  static boolean FieldSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldSuffix")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ( VariableDecl | Expr? SEMICOLON ) Expr? SEMICOLON Expr?
  static boolean ForHeader(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForHeader")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ForHeader_0(b, l + 1);
    r = r && ForHeader_1(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    r = r && ForHeader_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // VariableDecl | Expr? SEMICOLON
  private static boolean ForHeader_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForHeader_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = VariableDecl(b, l + 1);
    if (!r) r = ForHeader_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Expr? SEMICOLON
  private static boolean ForHeader_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForHeader_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ForHeader_0_1_0(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // Expr?
  private static boolean ForHeader_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForHeader_0_1_0")) return false;
    Expr(b, l + 1);
    return true;
  }

  // Expr?
  private static boolean ForHeader_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForHeader_1")) return false;
    Expr(b, l + 1);
    return true;
  }

  // Expr?
  private static boolean ForHeader_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForHeader_3")) return false;
    Expr(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // FOR LPAREN ForHeader RPAREN Statement
  public static boolean ForStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForStatement")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FOR, LPAREN);
    r = r && ForHeader(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && Statement(b, l + 1);
    exit_section_(b, m, FOR_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // FOR LPAREN ForeachVarDecl COLON Expr RPAREN Statement
  public static boolean ForeachStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForeachStatement")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FOR, LPAREN);
    r = r && ForeachVarDecl(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && Expr(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && Statement(b, l + 1);
    exit_section_(b, m, FOREACH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // DeclarationSpecifier identifier
  static boolean ForeachVarDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ForeachVarDecl")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = DeclarationSpecifier(b, l + 1);
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OVERRIDE_KW | FINAL_KW | EXPLICIT_KW | PROPERTY_KW
  static boolean FuncAttr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FuncAttr")) return false;
    boolean r;
    r = consumeToken(b, OVERRIDE_KW);
    if (!r) r = consumeToken(b, FINAL_KW);
    if (!r) r = consumeToken(b, EXPLICIT_KW);
    if (!r) r = consumeToken(b, PROPERTY_KW);
    return r;
  }

  /* ********************************************************** */
  // AccessSpecifier?
  //                  UFunctionDecl?
  //                  AccessSpecifier?
  //                  FunctionLocation?
  //                  AccessSpecifier?
  //                  (TypeRef AND? | TILDE)?
  //                  identifier
  //                  ParameterList
  //                  CONST?
  //                  FuncAttr?
  //                  (SEMICOLON | CompoundStatement SEMICOLON?)
  public static boolean FunctionDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_DECL, "<function decl>");
    r = FunctionDecl_0(b, l + 1);
    r = r && FunctionDecl_1(b, l + 1);
    r = r && FunctionDecl_2(b, l + 1);
    r = r && FunctionDecl_3(b, l + 1);
    r = r && FunctionDecl_4(b, l + 1);
    r = r && FunctionDecl_5(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && ParameterList(b, l + 1);
    r = r && FunctionDecl_8(b, l + 1);
    r = r && FunctionDecl_9(b, l + 1);
    r = r && FunctionDecl_10(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // AccessSpecifier?
  private static boolean FunctionDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_0")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // UFunctionDecl?
  private static boolean FunctionDecl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_1")) return false;
    UFunctionDecl(b, l + 1);
    return true;
  }

  // AccessSpecifier?
  private static boolean FunctionDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_2")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // FunctionLocation?
  private static boolean FunctionDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_3")) return false;
    FunctionLocation(b, l + 1);
    return true;
  }

  // AccessSpecifier?
  private static boolean FunctionDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_4")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // (TypeRef AND? | TILDE)?
  private static boolean FunctionDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_5")) return false;
    FunctionDecl_5_0(b, l + 1);
    return true;
  }

  // TypeRef AND? | TILDE
  private static boolean FunctionDecl_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = FunctionDecl_5_0_0(b, l + 1);
    if (!r) r = consumeToken(b, TILDE);
    exit_section_(b, m, null, r);
    return r;
  }

  // TypeRef AND?
  private static boolean FunctionDecl_5_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_5_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeRef(b, l + 1);
    r = r && FunctionDecl_5_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // AND?
  private static boolean FunctionDecl_5_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_5_0_0_1")) return false;
    consumeToken(b, AND);
    return true;
  }

  // CONST?
  private static boolean FunctionDecl_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_8")) return false;
    consumeToken(b, CONST);
    return true;
  }

  // FuncAttr?
  private static boolean FunctionDecl_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_9")) return false;
    FuncAttr(b, l + 1);
    return true;
  }

  // SEMICOLON | CompoundStatement SEMICOLON?
  private static boolean FunctionDecl_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_10")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    if (!r) r = FunctionDecl_10_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CompoundStatement SEMICOLON?
  private static boolean FunctionDecl_10_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_10_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = CompoundStatement(b, l + 1);
    r = r && FunctionDecl_10_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SEMICOLON?
  private static boolean FunctionDecl_10_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_10_1_1")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // (SHARED_KW | EXTERNAL_KW)+
  static boolean FunctionLocation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionLocation")) return false;
    if (!nextTokenIs(b, "", EXTERNAL_KW, SHARED_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = FunctionLocation_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!FunctionLocation_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FunctionLocation", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // SHARED_KW | EXTERNAL_KW
  private static boolean FunctionLocation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionLocation_0")) return false;
    boolean r;
    r = consumeToken(b, SHARED_KW);
    if (!r) r = consumeToken(b, EXTERNAL_KW);
    return r;
  }

  /* ********************************************************** */
  // PP_IF IfDefBranch (PP_ELIF IfDefBranch)* (PP_ELSE IfDefBranch)? PP_ENDIF
  public static boolean IfDefBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfDefBlock")) return false;
    if (!nextTokenIs(b, PP_IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IF_DEF_BLOCK, null);
    r = consumeToken(b, PP_IF);
    p = r; // pin = 1
    r = r && report_error_(b, IfDefBranch(b, l + 1));
    r = p && report_error_(b, IfDefBlock_2(b, l + 1)) && r;
    r = p && report_error_(b, IfDefBlock_3(b, l + 1)) && r;
    r = p && consumeToken(b, PP_ENDIF) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (PP_ELIF IfDefBranch)*
  private static boolean IfDefBlock_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfDefBlock_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!IfDefBlock_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "IfDefBlock_2", c)) break;
    }
    return true;
  }

  // PP_ELIF IfDefBranch
  private static boolean IfDefBlock_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfDefBlock_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PP_ELIF);
    r = r && IfDefBranch(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (PP_ELSE IfDefBranch)?
  private static boolean IfDefBlock_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfDefBlock_3")) return false;
    IfDefBlock_3_0(b, l + 1);
    return true;
  }

  // PP_ELSE IfDefBranch
  private static boolean IfDefBlock_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfDefBlock_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PP_ELSE);
    r = r && IfDefBranch(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Item*
  public static boolean IfDefBranch(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfDefBranch")) return false;
    Marker m = enter_section_(b, l, _NONE_, IF_DEF_BRANCH, "<if def branch>");
    while (true) {
      int c = current_position_(b);
      if (!Item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "IfDefBranch", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // IF ParenthesizedExpr Statement ElseClause?
  public static boolean IfStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfStatement")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && ParenthesizedExpr(b, l + 1);
    r = r && Statement(b, l + 1);
    r = r && IfStatement_3(b, l + 1);
    exit_section_(b, m, IF_STATEMENT, r);
    return r;
  }

  // ElseClause?
  private static boolean IfStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfStatement_3")) return false;
    ElseClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ExclusiveOrExpr (OR ExclusiveOrExpr)*
  public static boolean InclusiveOrExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InclusiveOrExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, INCLUSIVE_OR_EXPR, "<inclusive or expr>");
    r = ExclusiveOrExpr(b, l + 1);
    r = r && InclusiveOrExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (OR ExclusiveOrExpr)*
  private static boolean InclusiveOrExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InclusiveOrExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!InclusiveOrExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InclusiveOrExpr_1", c)) break;
    }
    return true;
  }

  // OR ExclusiveOrExpr
  private static boolean InclusiveOrExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InclusiveOrExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OR);
    r = r && ExclusiveOrExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // COLON TypeRef (COMMA TypeRef)*
  static boolean InheritanceList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InheritanceList")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && TypeRef(b, l + 1);
    r = r && InheritanceList_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA TypeRef)*
  private static boolean InheritanceList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InheritanceList_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!InheritanceList_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InheritanceList_2", c)) break;
    }
    return true;
  }

  // COMMA TypeRef
  private static boolean InheritanceList_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InheritanceList_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && TypeRef(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [TypeRef EQ] LBRACE [ Expr (COMMA Expr)* ] RBRACE
  public static boolean InitListExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INIT_LIST_EXPR, "<init list expr>");
    r = InitListExpr_0(b, l + 1);
    r = r && consumeToken(b, LBRACE);
    r = r && InitListExpr_2(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [TypeRef EQ]
  private static boolean InitListExpr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr_0")) return false;
    InitListExpr_0_0(b, l + 1);
    return true;
  }

  // TypeRef EQ
  private static boolean InitListExpr_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeRef(b, l + 1);
    r = r && consumeToken(b, EQ);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ Expr (COMMA Expr)* ]
  private static boolean InitListExpr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr_2")) return false;
    InitListExpr_2_0(b, l + 1);
    return true;
  }

  // Expr (COMMA Expr)*
  private static boolean InitListExpr_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Expr(b, l + 1);
    r = r && InitListExpr_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA Expr)*
  private static boolean InitListExpr_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!InitListExpr_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InitListExpr_2_0_1", c)) break;
    }
    return true;
  }

  // COMMA Expr
  private static boolean InitListExpr_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InitListExpr_2_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IfDefBlock
  //                | Declaration
  //                | Statement
  //                | Comment
  //                | SEMICOLON
  static boolean Item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Item")) return false;
    boolean r;
    r = IfDefBlock(b, l + 1);
    if (!r) r = Declaration(b, l + 1);
    if (!r) r = Statement(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // InclusiveOrExpr ((ANDAND | AND_KW) InclusiveOrExpr)*
  public static boolean LogicalAndExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, LOGICAL_AND_EXPR, "<logical and expr>");
    r = InclusiveOrExpr(b, l + 1);
    r = r && LogicalAndExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((ANDAND | AND_KW) InclusiveOrExpr)*
  private static boolean LogicalAndExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LogicalAndExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LogicalAndExpr_1", c)) break;
    }
    return true;
  }

  // (ANDAND | AND_KW) InclusiveOrExpr
  private static boolean LogicalAndExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LogicalAndExpr_1_0_0(b, l + 1);
    r = r && InclusiveOrExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ANDAND | AND_KW
  private static boolean LogicalAndExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, ANDAND);
    if (!r) r = consumeToken(b, AND_KW);
    return r;
  }

  /* ********************************************************** */
  // LogicalAndExpr ((OROR | OR_KW) LogicalAndExpr)*
  public static boolean LogicalOrExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, LOGICAL_OR_EXPR, "<logical or expr>");
    r = LogicalAndExpr(b, l + 1);
    r = r && LogicalOrExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((OROR | OR_KW) LogicalAndExpr)*
  private static boolean LogicalOrExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LogicalOrExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LogicalOrExpr_1", c)) break;
    }
    return true;
  }

  // (OROR | OR_KW) LogicalAndExpr
  private static boolean LogicalOrExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LogicalOrExpr_1_0_0(b, l + 1);
    r = r && LogicalAndExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // OROR | OR_KW
  private static boolean LogicalOrExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, OROR);
    if (!r) r = consumeToken(b, OR_KW);
    return r;
  }

  /* ********************************************************** */
  // identifier (EQ MetaArgValue)?
  static boolean MetaArg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArg")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && MetaArg_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (EQ MetaArgValue)?
  private static boolean MetaArg_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArg_1")) return false;
    MetaArg_1_0(b, l + 1);
    return true;
  }

  // EQ MetaArgValue
  private static boolean MetaArg_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArg_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQ);
    r = r && MetaArgValue(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LPAREN [ MetaArg (COMMA MetaArg)* COMMA? ] RPAREN
  static boolean MetaArgList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && MetaArgList_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ MetaArg (COMMA MetaArg)* COMMA? ]
  private static boolean MetaArgList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgList_1")) return false;
    MetaArgList_1_0(b, l + 1);
    return true;
  }

  // MetaArg (COMMA MetaArg)* COMMA?
  private static boolean MetaArgList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaArg(b, l + 1);
    r = r && MetaArgList_1_0_1(b, l + 1);
    r = r && MetaArgList_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA MetaArg)*
  private static boolean MetaArgList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgList_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MetaArgList_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaArgList_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA MetaArg
  private static boolean MetaArgList_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgList_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MetaArg(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA?
  private static boolean MetaArgList_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgList_1_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // LPAREN [ MetaArg (COMMA MetaArg)* COMMA? ] RPAREN
  //                        | Expr
  static boolean MetaArgValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaArgValue_0(b, l + 1);
    if (!r) r = Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // LPAREN [ MetaArg (COMMA MetaArg)* COMMA? ] RPAREN
  private static boolean MetaArgValue_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && MetaArgValue_0_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ MetaArg (COMMA MetaArg)* COMMA? ]
  private static boolean MetaArgValue_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue_0_1")) return false;
    MetaArgValue_0_1_0(b, l + 1);
    return true;
  }

  // MetaArg (COMMA MetaArg)* COMMA?
  private static boolean MetaArgValue_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaArg(b, l + 1);
    r = r && MetaArgValue_0_1_0_1(b, l + 1);
    r = r && MetaArgValue_0_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA MetaArg)*
  private static boolean MetaArgValue_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue_0_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MetaArgValue_0_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaArgValue_0_1_0_1", c)) break;
    }
    return true;
  }

  // COMMA MetaArg
  private static boolean MetaArgValue_0_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue_0_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MetaArg(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA?
  private static boolean MetaArgValue_0_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArgValue_0_1_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // UFunctionDecl? MIXIN_KW FunctionDecl
  public static boolean MixinDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MixinDecl")) return false;
    if (!nextTokenIs(b, "<mixin decl>", MIXIN_KW, UFUNCTION_KW)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MIXIN_DECL, "<mixin decl>");
    r = MixinDecl_0(b, l + 1);
    r = r && consumeToken(b, MIXIN_KW);
    r = r && FunctionDecl(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // UFunctionDecl?
  private static boolean MixinDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MixinDecl_0")) return false;
    UFunctionDecl(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // UnaryExpr ((MUL | POWER | DIV | REM) UnaryExpr)*
  public static boolean MultiplyExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, MULTIPLY_EXPR, "<multiply expr>");
    r = UnaryExpr(b, l + 1);
    r = r && MultiplyExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((MUL | POWER | DIV | REM) UnaryExpr)*
  private static boolean MultiplyExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MultiplyExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MultiplyExpr_1", c)) break;
    }
    return true;
  }

  // (MUL | POWER | DIV | REM) UnaryExpr
  private static boolean MultiplyExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MultiplyExpr_1_0_0(b, l + 1);
    r = r && UnaryExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MUL | POWER | DIV | REM
  private static boolean MultiplyExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, MUL);
    if (!r) r = consumeToken(b, POWER);
    if (!r) r = consumeToken(b, DIV);
    if (!r) r = consumeToken(b, REM);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_KW NamespacePath LBRACE NamespaceItem_with_recover* RBRACE SEMICOLON?
  public static boolean NamespaceDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceDecl")) return false;
    if (!nextTokenIs(b, NAMESPACE_KW)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NAMESPACE_DECL, null);
    r = consumeToken(b, NAMESPACE_KW);
    p = r; // pin = 1
    r = r && report_error_(b, NamespacePath(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LBRACE)) && r;
    r = p && report_error_(b, NamespaceDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RBRACE)) && r;
    r = p && NamespaceDecl_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // NamespaceItem_with_recover*
  private static boolean NamespaceDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceDecl_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!NamespaceItem_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "NamespaceDecl_3", c)) break;
    }
    return true;
  }

  // SEMICOLON?
  private static boolean NamespaceDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceDecl_5")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // !(RBRACE | CLASS | STRUCT | ENUM | EVENT_KW | DELEGATE_KW | MIXIN_KW
  //                                   | TYPEDEF_KW | NAMESPACE_KW | UCLASS_KW | USTRUCT_KW | UENUM_KW | UFUNCTION_KW | UPROPERTY_KW
  //                                   | DEFAULT_KW | IDENTIFIER | CONST | VOID_KW
  //                                   | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                   | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                   | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                   | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>)
  static boolean NamespaceItem_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceItem_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !NamespaceItem_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | CLASS | STRUCT | ENUM | EVENT_KW | DELEGATE_KW | MIXIN_KW
  //                                   | TYPEDEF_KW | NAMESPACE_KW | UCLASS_KW | USTRUCT_KW | UENUM_KW | UFUNCTION_KW | UPROPERTY_KW
  //                                   | DEFAULT_KW | IDENTIFIER | CONST | VOID_KW
  //                                   | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                   | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                   | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                   | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>
  private static boolean NamespaceItem_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceItem_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, CLASS);
    if (!r) r = consumeToken(b, STRUCT);
    if (!r) r = consumeToken(b, ENUM);
    if (!r) r = consumeToken(b, EVENT_KW);
    if (!r) r = consumeToken(b, DELEGATE_KW);
    if (!r) r = consumeToken(b, MIXIN_KW);
    if (!r) r = consumeToken(b, TYPEDEF_KW);
    if (!r) r = consumeToken(b, NAMESPACE_KW);
    if (!r) r = consumeToken(b, UCLASS_KW);
    if (!r) r = consumeToken(b, USTRUCT_KW);
    if (!r) r = consumeToken(b, UENUM_KW);
    if (!r) r = consumeToken(b, UFUNCTION_KW);
    if (!r) r = consumeToken(b, UPROPERTY_KW);
    if (!r) r = consumeToken(b, DEFAULT_KW);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, CONST);
    if (!r) r = consumeToken(b, VOID_KW);
    if (!r) r = consumeToken(b, INT_KW);
    if (!r) r = consumeToken(b, INT8_KW);
    if (!r) r = consumeToken(b, INT16_KW);
    if (!r) r = consumeToken(b, INT32_KW);
    if (!r) r = consumeToken(b, INT64_KW);
    if (!r) r = consumeToken(b, UINT_KW);
    if (!r) r = consumeToken(b, UINT8_KW);
    if (!r) r = consumeToken(b, UINT16_KW);
    if (!r) r = consumeToken(b, UINT32_KW);
    if (!r) r = consumeToken(b, UINT64_KW);
    if (!r) r = consumeToken(b, FLOAT_KW);
    if (!r) r = consumeToken(b, DOUBLE_KW);
    if (!r) r = consumeToken(b, BOOL_KW);
    if (!r) r = consumeToken(b, AUTO_KW);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, PP_IF);
    if (!r) r = consumeToken(b, PP_ELIF);
    if (!r) r = consumeToken(b, PP_ELSE);
    if (!r) r = consumeToken(b, PP_ENDIF);
    if (!r) r = consumeToken(b, PP_DEFINE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | <<eof>>) Item
  static boolean NamespaceItem_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceItem_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = NamespaceItem_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && Item(b, l + 1);
    exit_section_(b, l, m, r, p, AngelscriptParser::NamespaceItem_recover);
    return r || p;
  }

  // !(RBRACE | <<eof>>)
  private static boolean NamespaceItem_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceItem_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !NamespaceItem_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | <<eof>>
  private static boolean NamespaceItem_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespaceItem_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier (COLONCOLON identifier)*
  static boolean NamespacePath(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespacePath")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && NamespacePath_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COLONCOLON identifier)*
  private static boolean NamespacePath_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespacePath_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!NamespacePath_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "NamespacePath_1", c)) break;
    }
    return true;
  }

  // COLONCOLON identifier
  private static boolean NamespacePath_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamespacePath_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLONCOLON);
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // CompoundStatement
  //                            | ExpressionStatement
  //                            | IfStatement
  //                            | SwitchStatement
  //                            | DoStatement
  //                            | WhileStatement
  //                            | ForStatement
  //                            | ForeachStatement
  //                            | ReturnStatement
  //                            | BreakStatement
  //                            | ContinueStatement
  //                            | VariableDecl
  static boolean NonCaseStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NonCaseStatement")) return false;
    boolean r;
    r = CompoundStatement(b, l + 1);
    if (!r) r = ExpressionStatement(b, l + 1);
    if (!r) r = IfStatement(b, l + 1);
    if (!r) r = SwitchStatement(b, l + 1);
    if (!r) r = DoStatement(b, l + 1);
    if (!r) r = WhileStatement(b, l + 1);
    if (!r) r = ForStatement(b, l + 1);
    if (!r) r = ForeachStatement(b, l + 1);
    if (!r) r = ReturnStatement(b, l + 1);
    if (!r) r = BreakStatement(b, l + 1);
    if (!r) r = ContinueStatement(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // PP_DEFINE
  public static boolean PP_DefineDirective(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PP_DefineDirective")) return false;
    if (!nextTokenIs(b, PP_DEFINE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PP_DEFINE);
    exit_section_(b, m, PP_DEFINE_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // DeclarationSpecifier identifier [ EQ Expr ]
  public static boolean ParameterDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER_DECL, "<parameter decl>");
    r = DeclarationSpecifier(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && ParameterDecl_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [ EQ Expr ]
  private static boolean ParameterDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterDecl_2")) return false;
    ParameterDecl_2_0(b, l + 1);
    return true;
  }

  // EQ Expr
  private static boolean ParameterDecl_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterDecl_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQ);
    r = r && Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LPAREN [ VOID_KW | ParameterDecl (COMMA ParameterDecl)* COMMA? ] RPAREN
  public static boolean ParameterList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && ParameterList_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, PARAMETER_LIST, r);
    return r;
  }

  // [ VOID_KW | ParameterDecl (COMMA ParameterDecl)* COMMA? ]
  private static boolean ParameterList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1")) return false;
    ParameterList_1_0(b, l + 1);
    return true;
  }

  // VOID_KW | ParameterDecl (COMMA ParameterDecl)* COMMA?
  private static boolean ParameterList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VOID_KW);
    if (!r) r = ParameterList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ParameterDecl (COMMA ParameterDecl)* COMMA?
  private static boolean ParameterList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ParameterDecl(b, l + 1);
    r = r && ParameterList_1_0_1_1(b, l + 1);
    r = r && ParameterList_1_0_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA ParameterDecl)*
  private static boolean ParameterList_1_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1_0_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ParameterList_1_0_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ParameterList_1_0_1_1", c)) break;
    }
    return true;
  }

  // COMMA ParameterDecl
  private static boolean ParameterList_1_0_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1_0_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && ParameterDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA?
  private static boolean ParameterList_1_0_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1_0_1_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // LPAREN Expr RPAREN
  public static boolean ParenthesizedExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParenthesizedExpr")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && Expr(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, PARENTHESIZED_EXPR, r);
    return r;
  }

  /* ********************************************************** */
  // PrimaryExpr PostfixPart* PostfixUpdate?
  public static boolean PostfixExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PostfixExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, POSTFIX_EXPR, "<postfix expr>");
    r = PrimaryExpr(b, l + 1);
    r = r && PostfixExpr_1(b, l + 1);
    r = r && PostfixExpr_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PostfixPart*
  private static boolean PostfixExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PostfixExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!PostfixPart(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PostfixExpr_1", c)) break;
    }
    return true;
  }

  // PostfixUpdate?
  private static boolean PostfixExpr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PostfixExpr_2")) return false;
    PostfixUpdate(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ScopeSuffix
  //                       | FieldSuffix
  //                       | SubscriptSuffix
  //                       | CallSuffix
  static boolean PostfixPart(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PostfixPart")) return false;
    boolean r;
    r = ScopeSuffix(b, l + 1);
    if (!r) r = FieldSuffix(b, l + 1);
    if (!r) r = SubscriptSuffix(b, l + 1);
    if (!r) r = CallSuffix(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MINUSMINUS | PLUSPLUS
  static boolean PostfixUpdate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PostfixUpdate")) return false;
    if (!nextTokenIs(b, "", MINUSMINUS, PLUSPLUS)) return false;
    boolean r;
    r = consumeToken(b, MINUSMINUS);
    if (!r) r = consumeToken(b, PLUSPLUS);
    return r;
  }

  /* ********************************************************** */
  // AT | NOT_KW | EXCL | TILDE | MINUS | PLUS
  static boolean PrefixOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixOp")) return false;
    boolean r;
    r = consumeToken(b, AT);
    if (!r) r = consumeToken(b, NOT_KW);
    if (!r) r = consumeToken(b, EXCL);
    if (!r) r = consumeToken(b, TILDE);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, PLUS);
    return r;
  }

  /* ********************************************************** */
  // (MINUSMINUS | PLUSPLUS) UnaryExpr
  static boolean PrefixUpdateExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixUpdateExpr")) return false;
    if (!nextTokenIs(b, "", MINUSMINUS, PLUSPLUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrefixUpdateExpr_0(b, l + 1);
    r = r && UnaryExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MINUSMINUS | PLUSPLUS
  private static boolean PrefixUpdateExpr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixUpdateExpr_0")) return false;
    boolean r;
    r = consumeToken(b, MINUSMINUS);
    if (!r) r = consumeToken(b, PLUSPLUS);
    return r;
  }

  /* ********************************************************** */
  // VariableAccessExpr
  //               | InitListExpr
  //               | StringConcatExpr
  //               | number_literal
  //               | string_literal
  //               | bool_literal
  //               | null_literal
  //               | ParenthesizedExpr
  public static boolean PrimaryExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, PRIMARY_EXPR, "<primary expr>");
    r = VariableAccessExpr(b, l + 1);
    if (!r) r = InitListExpr(b, l + 1);
    if (!r) r = StringConcatExpr(b, l + 1);
    if (!r) r = number_literal(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    if (!r) r = bool_literal(b, l + 1);
    if (!r) r = null_literal(b, l + 1);
    if (!r) r = ParenthesizedExpr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // VOID_KW
  //                         | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                         | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                         | FLOAT_KW | DOUBLE_KW | BOOL_KW
  static boolean PrimitiveType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimitiveType")) return false;
    boolean r;
    r = consumeToken(b, VOID_KW);
    if (!r) r = consumeToken(b, INT_KW);
    if (!r) r = consumeToken(b, INT8_KW);
    if (!r) r = consumeToken(b, INT16_KW);
    if (!r) r = consumeToken(b, INT32_KW);
    if (!r) r = consumeToken(b, INT64_KW);
    if (!r) r = consumeToken(b, UINT_KW);
    if (!r) r = consumeToken(b, UINT8_KW);
    if (!r) r = consumeToken(b, UINT16_KW);
    if (!r) r = consumeToken(b, UINT32_KW);
    if (!r) r = consumeToken(b, UINT64_KW);
    if (!r) r = consumeToken(b, FLOAT_KW);
    if (!r) r = consumeToken(b, DOUBLE_KW);
    if (!r) r = consumeToken(b, BOOL_KW);
    return r;
  }

  /* ********************************************************** */
  // ShiftExpr ((GT | GTEQ | LTEQ | LT | IS_KW | EXCL IS_KW) ShiftExpr)*
  public static boolean RelationalExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, RELATIONAL_EXPR, "<relational expr>");
    r = ShiftExpr(b, l + 1);
    r = r && RelationalExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((GT | GTEQ | LTEQ | LT | IS_KW | EXCL IS_KW) ShiftExpr)*
  private static boolean RelationalExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!RelationalExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RelationalExpr_1", c)) break;
    }
    return true;
  }

  // (GT | GTEQ | LTEQ | LT | IS_KW | EXCL IS_KW) ShiftExpr
  private static boolean RelationalExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = RelationalExpr_1_0_0(b, l + 1);
    r = r && ShiftExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // GT | GTEQ | LTEQ | LT | IS_KW | EXCL IS_KW
  private static boolean RelationalExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, GT);
    if (!r) r = consumeToken(b, GTEQ);
    if (!r) r = consumeToken(b, LTEQ);
    if (!r) r = consumeToken(b, LT);
    if (!r) r = consumeToken(b, IS_KW);
    if (!r) r = parseTokens(b, 0, EXCL, IS_KW);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // RETURN Expr? SEMICOLON
  public static boolean ReturnStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReturnStatement")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RETURN);
    r = r && ReturnStatement_1(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, RETURN_STATEMENT, r);
    return r;
  }

  // Expr?
  private static boolean ReturnStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReturnStatement_1")) return false;
    Expr(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (identifier (COLONCOLON identifier)*)? TypeArgument? COLONCOLON
  public static boolean ScopeRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeRef")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SCOPE_REF, "<scope ref>");
    r = ScopeRef_0(b, l + 1);
    r = r && ScopeRef_1(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (identifier (COLONCOLON identifier)*)?
  private static boolean ScopeRef_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeRef_0")) return false;
    ScopeRef_0_0(b, l + 1);
    return true;
  }

  // identifier (COLONCOLON identifier)*
  private static boolean ScopeRef_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeRef_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && ScopeRef_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COLONCOLON identifier)*
  private static boolean ScopeRef_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeRef_0_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ScopeRef_0_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ScopeRef_0_0_1", c)) break;
    }
    return true;
  }

  // COLONCOLON identifier
  private static boolean ScopeRef_0_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeRef_0_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLONCOLON);
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TypeArgument?
  private static boolean ScopeRef_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeRef_1")) return false;
    TypeArgument(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // COLONCOLON identifier
  static boolean ScopeSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScopeSuffix")) return false;
    if (!nextTokenIs(b, COLONCOLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLONCOLON);
    r = r && identifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ScriptItem*
  static boolean Script(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Script")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ScriptItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Script", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !(<<eof>>) Item
  static boolean ScriptItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScriptItem")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = ScriptItem_0(b, l + 1);
    p = r; // pin = 1
    r = r && Item(b, l + 1);
    exit_section_(b, l, m, r, p, AngelscriptParser::ScriptItem_recover);
    return r || p;
  }

  // !(<<eof>>)
  private static boolean ScriptItem_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScriptItem_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ScriptItem_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<eof>>
  private static boolean ScriptItem_0_0(PsiBuilder b, int l) {
    return eof(b, l + 1);
  }

  /* ********************************************************** */
  // !(CLASS | STRUCT | ENUM | EVENT_KW | DELEGATE_KW | MIXIN_KW
  //                                | TYPEDEF_KW | NAMESPACE_KW | UCLASS_KW | USTRUCT_KW | UENUM_KW | UFUNCTION_KW | UPROPERTY_KW
  //                                | DEFAULT_KW | IDENTIFIER | CONST | VOID_KW
  //                                | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>)
  static boolean ScriptItem_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScriptItem_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ScriptItem_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // CLASS | STRUCT | ENUM | EVENT_KW | DELEGATE_KW | MIXIN_KW
  //                                | TYPEDEF_KW | NAMESPACE_KW | UCLASS_KW | USTRUCT_KW | UENUM_KW | UFUNCTION_KW | UPROPERTY_KW
  //                                | DEFAULT_KW | IDENTIFIER | CONST | VOID_KW
  //                                | INT_KW | INT8_KW | INT16_KW | INT32_KW | INT64_KW
  //                                | UINT_KW | UINT8_KW | UINT16_KW | UINT32_KW | UINT64_KW
  //                                | FLOAT_KW | DOUBLE_KW | BOOL_KW | AUTO_KW | AUTO
  //                                | PP_IF | PP_ELIF | PP_ELSE | PP_ENDIF | PP_DEFINE | <<eof>>
  private static boolean ScriptItem_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScriptItem_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CLASS);
    if (!r) r = consumeToken(b, STRUCT);
    if (!r) r = consumeToken(b, ENUM);
    if (!r) r = consumeToken(b, EVENT_KW);
    if (!r) r = consumeToken(b, DELEGATE_KW);
    if (!r) r = consumeToken(b, MIXIN_KW);
    if (!r) r = consumeToken(b, TYPEDEF_KW);
    if (!r) r = consumeToken(b, NAMESPACE_KW);
    if (!r) r = consumeToken(b, UCLASS_KW);
    if (!r) r = consumeToken(b, USTRUCT_KW);
    if (!r) r = consumeToken(b, UENUM_KW);
    if (!r) r = consumeToken(b, UFUNCTION_KW);
    if (!r) r = consumeToken(b, UPROPERTY_KW);
    if (!r) r = consumeToken(b, DEFAULT_KW);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, CONST);
    if (!r) r = consumeToken(b, VOID_KW);
    if (!r) r = consumeToken(b, INT_KW);
    if (!r) r = consumeToken(b, INT8_KW);
    if (!r) r = consumeToken(b, INT16_KW);
    if (!r) r = consumeToken(b, INT32_KW);
    if (!r) r = consumeToken(b, INT64_KW);
    if (!r) r = consumeToken(b, UINT_KW);
    if (!r) r = consumeToken(b, UINT8_KW);
    if (!r) r = consumeToken(b, UINT16_KW);
    if (!r) r = consumeToken(b, UINT32_KW);
    if (!r) r = consumeToken(b, UINT64_KW);
    if (!r) r = consumeToken(b, FLOAT_KW);
    if (!r) r = consumeToken(b, DOUBLE_KW);
    if (!r) r = consumeToken(b, BOOL_KW);
    if (!r) r = consumeToken(b, AUTO_KW);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, PP_IF);
    if (!r) r = consumeToken(b, PP_ELIF);
    if (!r) r = consumeToken(b, PP_ELSE);
    if (!r) r = consumeToken(b, PP_ENDIF);
    if (!r) r = consumeToken(b, PP_DEFINE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // AddExpr ((LTLT | GT GT) AddExpr)*
  public static boolean ShiftExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, SHIFT_EXPR, "<shift expr>");
    r = AddExpr(b, l + 1);
    r = r && ShiftExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((LTLT | GT GT) AddExpr)*
  private static boolean ShiftExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ShiftExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ShiftExpr_1", c)) break;
    }
    return true;
  }

  // (LTLT | GT GT) AddExpr
  private static boolean ShiftExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ShiftExpr_1_0_0(b, l + 1);
    r = r && AddExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // LTLT | GT GT
  private static boolean ShiftExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LTLT);
    if (!r) r = parseTokens(b, 0, GT, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // CaseStatement | NonCaseStatement
  public static boolean Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = CaseStatement(b, l + 1);
    if (!r) r = NonCaseStatement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // string_literal string_literal+
  public static boolean StringConcatExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringConcatExpr")) return false;
    if (!nextTokenIs(b, STRING_LITERAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string_literal(b, l + 1);
    r = r && StringConcatExpr_1(b, l + 1);
    exit_section_(b, m, STRING_CONCAT_EXPR, r);
    return r;
  }

  // string_literal+
  private static boolean StringConcatExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringConcatExpr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string_literal(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!string_literal(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StringConcatExpr_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // UStructDecl? STRUCT identifier LBRACE StructMember_with_recover* RBRACE SEMICOLON?
  public static boolean StructDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructDecl")) return false;
    if (!nextTokenIs(b, "<struct decl>", STRUCT, USTRUCT_KW)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCT_DECL, "<struct decl>");
    r = StructDecl_0(b, l + 1);
    r = r && consumeToken(b, STRUCT);
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, LBRACE);
    r = r && StructDecl_4(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    r = r && StructDecl_6(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // UStructDecl?
  private static boolean StructDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructDecl_0")) return false;
    UStructDecl(b, l + 1);
    return true;
  }

  // StructMember_with_recover*
  private static boolean StructDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructDecl_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!StructMember_with_recover(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StructDecl_4", c)) break;
    }
    return true;
  }

  // SEMICOLON?
  private static boolean StructDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructDecl_6")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // IfDefBlock | ConstructorDecl | FunctionDecl | DefaultValueDecl | VariableDecl | Comment
  static boolean StructMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructMember")) return false;
    boolean r;
    r = IfDefBlock(b, l + 1);
    if (!r) r = ConstructorDecl(b, l + 1);
    if (!r) r = FunctionDecl(b, l + 1);
    if (!r) r = DefaultValueDecl(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACE | <<eof>>) StructMember
  static boolean StructMember_with_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructMember_with_recover")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = StructMember_with_recover_0(b, l + 1);
    p = r; // pin = 1
    r = r && StructMember(b, l + 1);
    exit_section_(b, l, m, r, p, AngelscriptParser::ClassMember_recover);
    return r || p;
  }

  // !(RBRACE | <<eof>>)
  private static boolean StructMember_with_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructMember_with_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !StructMember_with_recover_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | <<eof>>
  private static boolean StructMember_with_recover_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructMember_with_recover_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LBRACK Expr RBRACK
  static boolean SubscriptSuffix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SubscriptSuffix")) return false;
    if (!nextTokenIs(b, LBRACK)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACK);
    r = r && Expr(b, l + 1);
    r = r && consumeToken(b, RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SWITCH_KW ParenthesizedExpr CompoundStatement
  public static boolean SwitchStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SwitchStatement")) return false;
    if (!nextTokenIs(b, SWITCH_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWITCH_KW);
    r = r && ParenthesizedExpr(b, l + 1);
    r = r && CompoundStatement(b, l + 1);
    exit_section_(b, m, SWITCH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // LT TypeRef (COMMA TypeRef)* GT
  public static boolean TypeArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeArgument")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && TypeRef(b, l + 1);
    r = r && TypeArgument_2(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, TYPE_ARGUMENT, r);
    return r;
  }

  // (COMMA TypeRef)*
  private static boolean TypeArgument_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeArgument_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!TypeArgument_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeArgument_2", c)) break;
    }
    return true;
  }

  // COMMA TypeRef
  private static boolean TypeArgument_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeArgument_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && TypeRef(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // AND (IN | OUT | INOUT_KW)?
  static boolean TypeMod(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeMod")) return false;
    if (!nextTokenIs(b, AND)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AND);
    r = r && TypeMod_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (IN | OUT | INOUT_KW)?
  private static boolean TypeMod_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeMod_1")) return false;
    TypeMod_1_0(b, l + 1);
    return true;
  }

  // IN | OUT | INOUT_KW
  private static boolean TypeMod_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeMod_1_0")) return false;
    boolean r;
    r = consumeToken(b, IN);
    if (!r) r = consumeToken(b, OUT);
    if (!r) r = consumeToken(b, INOUT_KW);
    return r;
  }

  /* ********************************************************** */
  // CONST?
  //             ScopeRef?
  //             DataType
  //             TypeArgument*
  //             ( LBRACK RBRACK | AT CONST? )*
  public static boolean TypeRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_REF, "<type ref>");
    r = TypeRef_0(b, l + 1);
    r = r && TypeRef_1(b, l + 1);
    r = r && DataType(b, l + 1);
    r = r && TypeRef_3(b, l + 1);
    r = r && TypeRef_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // CONST?
  private static boolean TypeRef_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_0")) return false;
    consumeToken(b, CONST);
    return true;
  }

  // ScopeRef?
  private static boolean TypeRef_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_1")) return false;
    ScopeRef(b, l + 1);
    return true;
  }

  // TypeArgument*
  private static boolean TypeRef_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!TypeArgument(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeRef_3", c)) break;
    }
    return true;
  }

  // ( LBRACK RBRACK | AT CONST? )*
  private static boolean TypeRef_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!TypeRef_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeRef_4", c)) break;
    }
    return true;
  }

  // LBRACK RBRACK | AT CONST?
  private static boolean TypeRef_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseTokens(b, 0, LBRACK, RBRACK);
    if (!r) r = TypeRef_4_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // AT CONST?
  private static boolean TypeRef_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AT);
    r = r && TypeRef_4_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CONST?
  private static boolean TypeRef_4_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4_0_1_1")) return false;
    consumeToken(b, CONST);
    return true;
  }

  /* ********************************************************** */
  // TYPEDEF_KW PrimitiveType identifier SEMICOLON
  public static boolean TypedefDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypedefDecl")) return false;
    if (!nextTokenIs(b, TYPEDEF_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPEDEF_KW);
    r = r && PrimitiveType(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, TYPEDEF_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // UCLASS_KW    MetaArgList
  public static boolean UClassDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UClassDecl")) return false;
    if (!nextTokenIs(b, UCLASS_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UCLASS_KW);
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, m, U_CLASS_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // UENUM_KW     MetaArgList
  public static boolean UEnumDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UEnumDecl")) return false;
    if (!nextTokenIs(b, UENUM_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UENUM_KW);
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, m, U_ENUM_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // UFUNCTION_KW MetaArgList
  public static boolean UFunctionDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UFunctionDecl")) return false;
    if (!nextTokenIs(b, UFUNCTION_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UFUNCTION_KW);
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, m, U_FUNCTION_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // UPROPERTY_KW MetaArgList
  public static boolean UPropertyDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UPropertyDecl")) return false;
    if (!nextTokenIs(b, UPROPERTY_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, UPROPERTY_KW);
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, m, U_PROPERTY_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // USTRUCT_KW   MetaArgList
  public static boolean UStructDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UStructDecl")) return false;
    if (!nextTokenIs(b, USTRUCT_KW)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, USTRUCT_KW);
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, m, U_STRUCT_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // PrefixOp UnaryExpr
  //             | PrefixUpdateExpr
  //             | CastExpr
  //             | PostfixExpr
  public static boolean UnaryExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnaryExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, UNARY_EXPR, "<unary expr>");
    r = UnaryExpr_0(b, l + 1);
    if (!r) r = PrefixUpdateExpr(b, l + 1);
    if (!r) r = CastExpr(b, l + 1);
    if (!r) r = PostfixExpr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PrefixOp UnaryExpr
  private static boolean UnaryExpr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnaryExpr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrefixOp(b, l + 1);
    r = r && UnaryExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ScopeRef? identifier
  public static boolean VariableAccessExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableAccessExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VARIABLE_ACCESS_EXPR, "<variable access expr>");
    r = VariableAccessExpr_0(b, l + 1);
    r = r && identifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ScopeRef?
  private static boolean VariableAccessExpr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableAccessExpr_0")) return false;
    ScopeRef(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // AccessSpecifier?
  //                  UPropertyDecl?
  //                  AccessSpecifier?
  //                  TypeRef
  //                  TypeMod?
  //                  VariableItem (COMMA VariableItem)*
  //                  SEMICOLON
  public static boolean VariableDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VARIABLE_DECL, "<variable decl>");
    r = VariableDecl_0(b, l + 1);
    r = r && VariableDecl_1(b, l + 1);
    r = r && VariableDecl_2(b, l + 1);
    r = r && TypeRef(b, l + 1);
    r = r && VariableDecl_4(b, l + 1);
    r = r && VariableItem(b, l + 1);
    r = r && VariableDecl_6(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // AccessSpecifier?
  private static boolean VariableDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_0")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // UPropertyDecl?
  private static boolean VariableDecl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_1")) return false;
    UPropertyDecl(b, l + 1);
    return true;
  }

  // AccessSpecifier?
  private static boolean VariableDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_2")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // TypeMod?
  private static boolean VariableDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_4")) return false;
    TypeMod(b, l + 1);
    return true;
  }

  // (COMMA VariableItem)*
  private static boolean VariableDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_6")) return false;
    while (true) {
      int c = current_position_(b);
      if (!VariableDecl_6_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "VariableDecl_6", c)) break;
    }
    return true;
  }

  // COMMA VariableItem
  private static boolean VariableDecl_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && VariableItem(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier ( EQ Expr | ArgList )?
  static boolean VariableItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableItem")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && VariableItem_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( EQ Expr | ArgList )?
  private static boolean VariableItem_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableItem_1")) return false;
    VariableItem_1_0(b, l + 1);
    return true;
  }

  // EQ Expr | ArgList
  private static boolean VariableItem_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableItem_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = VariableItem_1_0_0(b, l + 1);
    if (!r) r = ArgList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // EQ Expr
  private static boolean VariableItem_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableItem_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQ);
    r = r && Expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // WHILE ParenthesizedExpr Statement
  public static boolean WhileStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhileStatement")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WHILE);
    r = r && ParenthesizedExpr(b, l + 1);
    r = r && Statement(b, l + 1);
    exit_section_(b, m, WHILE_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // BOOL_LITERAL
  static boolean bool_literal(PsiBuilder b, int l) {
    return consumeToken(b, BOOL_LITERAL);
  }

  /* ********************************************************** */
  // IDENTIFIER
  static boolean identifier(PsiBuilder b, int l) {
    return consumeToken(b, IDENTIFIER);
  }

  /* ********************************************************** */
  // NULLPTR_KW
  static boolean null_literal(PsiBuilder b, int l) {
    return consumeToken(b, NULLPTR_KW);
  }

  /* ********************************************************** */
  // NUMBER_LITERAL
  static boolean number_literal(PsiBuilder b, int l) {
    return consumeToken(b, NUMBER_LITERAL);
  }

  /* ********************************************************** */
  // STRING_LITERAL
  static boolean string_literal(PsiBuilder b, int l) {
    return consumeToken(b, STRING_LITERAL);
  }

}
