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
  // 'private' | 'protected'
  static boolean AccessSpecifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessSpecifier")) return false;
    boolean r;
    r = consumeToken(b, "private");
    if (!r) r = consumeToken(b, "protected");
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
  // EQ | MULEQ | '**=' | DIVEQ | REMEQ | PLUSEQ | MINUSEQ | '<<=' | '>>=' | '>>>=' | ANDEQ | XOREQ | OREQ
  static boolean AssignmentOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignmentOp")) return false;
    boolean r;
    r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, MULEQ);
    if (!r) r = consumeToken(b, "**=");
    if (!r) r = consumeToken(b, DIVEQ);
    if (!r) r = consumeToken(b, REMEQ);
    if (!r) r = consumeToken(b, PLUSEQ);
    if (!r) r = consumeToken(b, MINUSEQ);
    if (!r) r = consumeToken(b, "<<=");
    if (!r) r = consumeToken(b, ">>=");
    if (!r) r = consumeToken(b, ">>>=");
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
  // Declaration | Statement | Comment
  static boolean BlockItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockItem")) return false;
    boolean r;
    r = Declaration(b, l + 1);
    if (!r) r = Statement(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // BREAK SEMICOLON
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
  // ( 'case' Expr | 'default' ) COLON (NonCaseStatement | VariableDecl)*
  public static boolean CaseStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CASE_STATEMENT, "<case statement>");
    r = CaseStatement_0(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && CaseStatement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'case' Expr | 'default'
  private static boolean CaseStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = CaseStatement_0_0(b, l + 1);
    if (!r) r = consumeToken(b, "default");
    exit_section_(b, m, null, r);
    return r;
  }

  // 'case' Expr
  private static boolean CaseStatement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CaseStatement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "case");
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
  // UClassDecl? CLASS identifier InheritanceList? LBRACE ClassMember* RBRACE SEMICOLON?
  public static boolean ClassDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl")) return false;
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

  // ClassMember*
  private static boolean ClassDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassDecl_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ClassMember(b, l + 1)) break;
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
  // FunctionDecl | VariableDecl | Comment
  static boolean ClassMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ClassMember")) return false;
    boolean r;
    r = FunctionDecl(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    if (!r) r = Comment(b, l + 1);
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
  // LBRACE BlockItem* RBRACE
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

  // BlockItem*
  private static boolean CompoundStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CompoundStatement_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!BlockItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "CompoundStatement_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LogicalOrExpr ('?' Expr? COLON Expr)?
  public static boolean ConditionalExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, CONDITIONAL_EXPR, "<conditional expr>");
    r = LogicalOrExpr(b, l + 1);
    r = r && ConditionalExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('?' Expr? COLON Expr)?
  private static boolean ConditionalExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr_1")) return false;
    ConditionalExpr_1_0(b, l + 1);
    return true;
  }

  // '?' Expr? COLON Expr
  private static boolean ConditionalExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConditionalExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "?");
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
  // identifier | PrimitiveType | '?' | 'auto'
  static boolean DataType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DataType")) return false;
    boolean r;
    r = identifier(b, l + 1);
    if (!r) r = PrimitiveType(b, l + 1);
    if (!r) r = consumeToken(b, "?");
    if (!r) r = consumeToken(b, "auto");
    return r;
  }

  /* ********************************************************** */
  // TypedefDecl
  //                       | ClassDecl
  //                       | StructDecl
  //                       | EventDecl
  //                       | DelegateDecl
  //                       | MixinDecl
  //                       | FunctionDecl
  //                       | VariableDecl
  //                       | Comment
  static boolean Declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Declaration")) return false;
    boolean r;
    r = TypedefDecl(b, l + 1);
    if (!r) r = ClassDecl(b, l + 1);
    if (!r) r = StructDecl(b, l + 1);
    if (!r) r = EventDecl(b, l + 1);
    if (!r) r = DelegateDecl(b, l + 1);
    if (!r) r = MixinDecl(b, l + 1);
    if (!r) r = FunctionDecl(b, l + 1);
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
  // 'delegate' FunctionDecl
  public static boolean DelegateDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DelegateDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DELEGATE_DECL, "<delegate decl>");
    r = consumeToken(b, "delegate");
    r = r && FunctionDecl(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'do' Statement WHILE ParenthesizedExpr SEMICOLON
  public static boolean DoStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DoStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DO_STATEMENT, "<do statement>");
    r = consumeToken(b, "do");
    r = r && Statement(b, l + 1);
    r = r && consumeToken(b, WHILE);
    r = r && ParenthesizedExpr(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // STRING_LITERAL
  static boolean DoubleQuotedString(PsiBuilder b, int l) {
    return consumeToken(b, STRING_LITERAL);
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
  // 'event' FunctionDecl
  public static boolean EventDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EventDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EVENT_DECL, "<event decl>");
    r = consumeToken(b, "event");
    r = r && FunctionDecl(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BitwiseAndExpr ((XOR | 'xor') BitwiseAndExpr)*
  public static boolean ExclusiveOrExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, EXCLUSIVE_OR_EXPR, "<exclusive or expr>");
    r = BitwiseAndExpr(b, l + 1);
    r = r && ExclusiveOrExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((XOR | 'xor') BitwiseAndExpr)*
  private static boolean ExclusiveOrExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ExclusiveOrExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ExclusiveOrExpr_1", c)) break;
    }
    return true;
  }

  // (XOR | 'xor') BitwiseAndExpr
  private static boolean ExclusiveOrExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ExclusiveOrExpr_1_0_0(b, l + 1);
    r = r && BitwiseAndExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // XOR | 'xor'
  private static boolean ExclusiveOrExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExclusiveOrExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, XOR);
    if (!r) r = consumeToken(b, "xor");
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
  // 'override' | 'final' | 'explicit' | 'property'
  static boolean FuncAttr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FuncAttr")) return false;
    boolean r;
    r = consumeToken(b, "override");
    if (!r) r = consumeToken(b, "final");
    if (!r) r = consumeToken(b, "explicit");
    if (!r) r = consumeToken(b, "property");
    return r;
  }

  /* ********************************************************** */
  // UFunctionDecl?
  //                  FunctionLocation?
  //                  AccessSpecifier?
  //                  (TypeRef AND? | '~')?
  //                  identifier
  //                  ParameterList
  //                  CONST?
  //                  FuncAttr?
  //                  (SEMICOLON | CompoundStatement)
  public static boolean FunctionDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_DECL, "<function decl>");
    r = FunctionDecl_0(b, l + 1);
    r = r && FunctionDecl_1(b, l + 1);
    r = r && FunctionDecl_2(b, l + 1);
    r = r && FunctionDecl_3(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && ParameterList(b, l + 1);
    r = r && FunctionDecl_6(b, l + 1);
    r = r && FunctionDecl_7(b, l + 1);
    r = r && FunctionDecl_8(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // UFunctionDecl?
  private static boolean FunctionDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_0")) return false;
    UFunctionDecl(b, l + 1);
    return true;
  }

  // FunctionLocation?
  private static boolean FunctionDecl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_1")) return false;
    FunctionLocation(b, l + 1);
    return true;
  }

  // AccessSpecifier?
  private static boolean FunctionDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_2")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // (TypeRef AND? | '~')?
  private static boolean FunctionDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_3")) return false;
    FunctionDecl_3_0(b, l + 1);
    return true;
  }

  // TypeRef AND? | '~'
  private static boolean FunctionDecl_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = FunctionDecl_3_0_0(b, l + 1);
    if (!r) r = consumeToken(b, "~");
    exit_section_(b, m, null, r);
    return r;
  }

  // TypeRef AND?
  private static boolean FunctionDecl_3_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_3_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeRef(b, l + 1);
    r = r && FunctionDecl_3_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // AND?
  private static boolean FunctionDecl_3_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_3_0_0_1")) return false;
    consumeToken(b, AND);
    return true;
  }

  // CONST?
  private static boolean FunctionDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_6")) return false;
    consumeToken(b, CONST);
    return true;
  }

  // FuncAttr?
  private static boolean FunctionDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_7")) return false;
    FuncAttr(b, l + 1);
    return true;
  }

  // SEMICOLON | CompoundStatement
  private static boolean FunctionDecl_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionDecl_8")) return false;
    boolean r;
    r = consumeToken(b, SEMICOLON);
    if (!r) r = CompoundStatement(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ('shared' | 'external')+
  static boolean FunctionLocation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionLocation")) return false;
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

  // 'shared' | 'external'
  private static boolean FunctionLocation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionLocation_0")) return false;
    boolean r;
    r = consumeToken(b, "shared");
    if (!r) r = consumeToken(b, "external");
    return r;
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
  // Declaration
  //                | Statement
  //                | Comment
  //                | SEMICOLON
  static boolean Item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Item")) return false;
    boolean r;
    r = Declaration(b, l + 1);
    if (!r) r = Statement(b, l + 1);
    if (!r) r = Comment(b, l + 1);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // InclusiveOrExpr (('&&' | 'and') InclusiveOrExpr)*
  public static boolean LogicalAndExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, LOGICAL_AND_EXPR, "<logical and expr>");
    r = InclusiveOrExpr(b, l + 1);
    r = r && LogicalAndExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('&&' | 'and') InclusiveOrExpr)*
  private static boolean LogicalAndExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LogicalAndExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LogicalAndExpr_1", c)) break;
    }
    return true;
  }

  // ('&&' | 'and') InclusiveOrExpr
  private static boolean LogicalAndExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LogicalAndExpr_1_0_0(b, l + 1);
    r = r && InclusiveOrExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '&&' | 'and'
  private static boolean LogicalAndExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalAndExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, "&&");
    if (!r) r = consumeToken(b, "and");
    return r;
  }

  /* ********************************************************** */
  // LogicalAndExpr (('||' | 'or') LogicalAndExpr)*
  public static boolean LogicalOrExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, LOGICAL_OR_EXPR, "<logical or expr>");
    r = LogicalAndExpr(b, l + 1);
    r = r && LogicalOrExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('||' | 'or') LogicalAndExpr)*
  private static boolean LogicalOrExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!LogicalOrExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LogicalOrExpr_1", c)) break;
    }
    return true;
  }

  // ('||' | 'or') LogicalAndExpr
  private static boolean LogicalOrExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LogicalOrExpr_1_0_0(b, l + 1);
    r = r && LogicalAndExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '||' | 'or'
  private static boolean LogicalOrExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LogicalOrExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, "||");
    if (!r) r = consumeToken(b, "or");
    return r;
  }

  /* ********************************************************** */
  // identifier | identifier EQ LPAREN Expr RPAREN
  static boolean MetaArg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArg")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    if (!r) r = MetaArg_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // identifier EQ LPAREN Expr RPAREN
  private static boolean MetaArg_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaArg_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && consumeTokens(b, 0, EQ, LPAREN);
    r = r && Expr(b, l + 1);
    r = r && consumeToken(b, RPAREN);
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
  // 'mixin' FunctionDecl
  public static boolean MixinDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MixinDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MIXIN_DECL, "<mixin decl>");
    r = consumeToken(b, "mixin");
    r = r && FunctionDecl(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // STRING_LITERAL
  static boolean MultilineString(PsiBuilder b, int l) {
    return consumeToken(b, STRING_LITERAL);
  }

  /* ********************************************************** */
  // UnaryExpr ((MUL | '**' | DIV | REM) UnaryExpr)*
  public static boolean MultiplyExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, MULTIPLY_EXPR, "<multiply expr>");
    r = UnaryExpr(b, l + 1);
    r = r && MultiplyExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((MUL | '**' | DIV | REM) UnaryExpr)*
  private static boolean MultiplyExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!MultiplyExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MultiplyExpr_1", c)) break;
    }
    return true;
  }

  // (MUL | '**' | DIV | REM) UnaryExpr
  private static boolean MultiplyExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MultiplyExpr_1_0_0(b, l + 1);
    r = r && UnaryExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MUL | '**' | DIV | REM
  private static boolean MultiplyExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiplyExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, MUL);
    if (!r) r = consumeToken(b, "**");
    if (!r) r = consumeToken(b, DIV);
    if (!r) r = consumeToken(b, REM);
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
  // LPAREN [ 'void' | ParameterDecl (COMMA ParameterDecl)* COMMA? ] RPAREN
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

  // [ 'void' | ParameterDecl (COMMA ParameterDecl)* COMMA? ]
  private static boolean ParameterList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1")) return false;
    ParameterList_1_0(b, l + 1);
    return true;
  }

  // 'void' | ParameterDecl (COMMA ParameterDecl)* COMMA?
  private static boolean ParameterList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "void");
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
  // '--' | '++'
  static boolean PostfixUpdate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PostfixUpdate")) return false;
    boolean r;
    r = consumeToken(b, "--");
    if (!r) r = consumeToken(b, "++");
    return r;
  }

  /* ********************************************************** */
  // '@' | 'not' | EXCL | '~' | MINUS | PLUS
  static boolean PrefixOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixOp")) return false;
    boolean r;
    r = consumeToken(b, "@");
    if (!r) r = consumeToken(b, "not");
    if (!r) r = consumeToken(b, EXCL);
    if (!r) r = consumeToken(b, "~");
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, PLUS);
    return r;
  }

  /* ********************************************************** */
  // ('--' | '++') UnaryExpr
  static boolean PrefixUpdateExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixUpdateExpr")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrefixUpdateExpr_0(b, l + 1);
    r = r && UnaryExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '--' | '++'
  private static boolean PrefixUpdateExpr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixUpdateExpr_0")) return false;
    boolean r;
    r = consumeToken(b, "--");
    if (!r) r = consumeToken(b, "++");
    return r;
  }

  /* ********************************************************** */
  // identifier (DoubleQuotedString | SingleQuotedString | MultilineString)
  static boolean PrefixedStringLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixedStringLiteral")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier(b, l + 1);
    r = r && PrefixedStringLiteral_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DoubleQuotedString | SingleQuotedString | MultilineString
  private static boolean PrefixedStringLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrefixedStringLiteral_1")) return false;
    boolean r;
    r = DoubleQuotedString(b, l + 1);
    if (!r) r = SingleQuotedString(b, l + 1);
    if (!r) r = MultilineString(b, l + 1);
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
  // 'void'
  //                         | 'int' | 'int8' | 'int16' | 'int32' | 'int64'
  //                         | 'uint' | 'uint8' | 'uint16' | 'uint32' | 'uint64'
  //                         | 'float' | 'double' | 'bool'
  static boolean PrimitiveType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimitiveType")) return false;
    boolean r;
    r = consumeToken(b, "void");
    if (!r) r = consumeToken(b, "int");
    if (!r) r = consumeToken(b, "int8");
    if (!r) r = consumeToken(b, "int16");
    if (!r) r = consumeToken(b, "int32");
    if (!r) r = consumeToken(b, "int64");
    if (!r) r = consumeToken(b, "uint");
    if (!r) r = consumeToken(b, "uint8");
    if (!r) r = consumeToken(b, "uint16");
    if (!r) r = consumeToken(b, "uint32");
    if (!r) r = consumeToken(b, "uint64");
    if (!r) r = consumeToken(b, "float");
    if (!r) r = consumeToken(b, "double");
    if (!r) r = consumeToken(b, "bool");
    return r;
  }

  /* ********************************************************** */
  // ShiftExpr ((GT | '>=' | '<=' | LT | 'is' | '!is') ShiftExpr)*
  public static boolean RelationalExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, RELATIONAL_EXPR, "<relational expr>");
    r = ShiftExpr(b, l + 1);
    r = r && RelationalExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((GT | '>=' | '<=' | LT | 'is' | '!is') ShiftExpr)*
  private static boolean RelationalExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!RelationalExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RelationalExpr_1", c)) break;
    }
    return true;
  }

  // (GT | '>=' | '<=' | LT | 'is' | '!is') ShiftExpr
  private static boolean RelationalExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = RelationalExpr_1_0_0(b, l + 1);
    r = r && ShiftExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // GT | '>=' | '<=' | LT | 'is' | '!is'
  private static boolean RelationalExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, GT);
    if (!r) r = consumeToken(b, ">=");
    if (!r) r = consumeToken(b, "<=");
    if (!r) r = consumeToken(b, LT);
    if (!r) r = consumeToken(b, "is");
    if (!r) r = consumeToken(b, "!is");
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
  // Item*
  static boolean Script(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Script")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Script", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // AddExpr (('<<' | '>>' | '>>>') AddExpr)*
  public static boolean ShiftExpr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, SHIFT_EXPR, "<shift expr>");
    r = AddExpr(b, l + 1);
    r = r && ShiftExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('<<' | '>>' | '>>>') AddExpr)*
  private static boolean ShiftExpr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ShiftExpr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ShiftExpr_1", c)) break;
    }
    return true;
  }

  // ('<<' | '>>' | '>>>') AddExpr
  private static boolean ShiftExpr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ShiftExpr_1_0_0(b, l + 1);
    r = r && AddExpr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<<' | '>>' | '>>>'
  private static boolean ShiftExpr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ShiftExpr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, "<<");
    if (!r) r = consumeToken(b, ">>");
    if (!r) r = consumeToken(b, ">>>");
    return r;
  }

  /* ********************************************************** */
  // STRING_LITERAL
  static boolean SingleQuotedString(PsiBuilder b, int l) {
    return consumeToken(b, STRING_LITERAL);
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
    if (!nextTokenIs(b, "<string concat expr>", IDENTIFIER, STRING_LITERAL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRING_CONCAT_EXPR, "<string concat expr>");
    r = string_literal(b, l + 1);
    r = r && StringConcatExpr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
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
  // UStructDecl? STRUCT identifier LBRACE StructMember* RBRACE SEMICOLON?
  public static boolean StructDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructDecl")) return false;
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

  // StructMember*
  private static boolean StructDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructDecl_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!StructMember(b, l + 1)) break;
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
  // FunctionDecl | VariableDecl | Comment
  static boolean StructMember(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StructMember")) return false;
    boolean r;
    r = FunctionDecl(b, l + 1);
    if (!r) r = VariableDecl(b, l + 1);
    if (!r) r = Comment(b, l + 1);
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
  // 'switch' ParenthesizedExpr CompoundStatement
  public static boolean SwitchStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SwitchStatement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SWITCH_STATEMENT, "<switch statement>");
    r = consumeToken(b, "switch");
    r = r && ParenthesizedExpr(b, l + 1);
    r = r && CompoundStatement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
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
  // AND (IN | OUT | 'inout')?
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

  // (IN | OUT | 'inout')?
  private static boolean TypeMod_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeMod_1")) return false;
    TypeMod_1_0(b, l + 1);
    return true;
  }

  // IN | OUT | 'inout'
  private static boolean TypeMod_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeMod_1_0")) return false;
    boolean r;
    r = consumeToken(b, IN);
    if (!r) r = consumeToken(b, OUT);
    if (!r) r = consumeToken(b, "inout");
    return r;
  }

  /* ********************************************************** */
  // CONST?
  //             ScopeRef?
  //             DataType
  //             TypeArgument*
  //             ( LBRACK RBRACK | '@' CONST? )*
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

  // ( LBRACK RBRACK | '@' CONST? )*
  private static boolean TypeRef_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!TypeRef_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeRef_4", c)) break;
    }
    return true;
  }

  // LBRACK RBRACK | '@' CONST?
  private static boolean TypeRef_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseTokens(b, 0, LBRACK, RBRACK);
    if (!r) r = TypeRef_4_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '@' CONST?
  private static boolean TypeRef_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef_4_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "@");
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
  // 'typedef' PrimitiveType identifier SEMICOLON
  public static boolean TypedefDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypedefDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPEDEF_DECL, "<typedef decl>");
    r = consumeToken(b, "typedef");
    r = r && PrimitiveType(b, l + 1);
    r = r && identifier(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'UCLASS' MetaArgList
  public static boolean UClassDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UClassDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, U_CLASS_DECL, "<u class decl>");
    r = consumeToken(b, "UCLASS");
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'UFUNCTION' MetaArgList
  public static boolean UFunctionDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UFunctionDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, U_FUNCTION_DECL, "<u function decl>");
    r = consumeToken(b, "UFUNCTION");
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'UPROPERTY' MetaArgList
  public static boolean UPropertyDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UPropertyDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, U_PROPERTY_DECL, "<u property decl>");
    r = consumeToken(b, "UPROPERTY");
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'USTRUCT' MetaArgList
  public static boolean UStructDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UStructDecl")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, U_STRUCT_DECL, "<u struct decl>");
    r = consumeToken(b, "USTRUCT");
    r = r && MetaArgList(b, l + 1);
    exit_section_(b, l, m, r, false, null);
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
  // UPropertyDecl?
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
    r = r && TypeRef(b, l + 1);
    r = r && VariableDecl_3(b, l + 1);
    r = r && VariableItem(b, l + 1);
    r = r && VariableDecl_5(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // UPropertyDecl?
  private static boolean VariableDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_0")) return false;
    UPropertyDecl(b, l + 1);
    return true;
  }

  // AccessSpecifier?
  private static boolean VariableDecl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_1")) return false;
    AccessSpecifier(b, l + 1);
    return true;
  }

  // TypeMod?
  private static boolean VariableDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_3")) return false;
    TypeMod(b, l + 1);
    return true;
  }

  // (COMMA VariableItem)*
  private static boolean VariableDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!VariableDecl_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "VariableDecl_5", c)) break;
    }
    return true;
  }

  // COMMA VariableItem
  private static boolean VariableDecl_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VariableDecl_5_0")) return false;
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
  // 'nullptr'
  static boolean null_literal(PsiBuilder b, int l) {
    return consumeToken(b, "nullptr");
  }

  /* ********************************************************** */
  // NUMBER_LITERAL
  static boolean number_literal(PsiBuilder b, int l) {
    return consumeToken(b, NUMBER_LITERAL);
  }

  /* ********************************************************** */
  // PrefixedStringLiteral
  //                          | DoubleQuotedString
  //                          | SingleQuotedString
  //                          | MultilineString
  static boolean string_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_literal")) return false;
    if (!nextTokenIs(b, "", IDENTIFIER, STRING_LITERAL)) return false;
    boolean r;
    r = PrefixedStringLiteral(b, l + 1);
    if (!r) r = DoubleQuotedString(b, l + 1);
    if (!r) r = SingleQuotedString(b, l + 1);
    if (!r) r = MultilineString(b, l + 1);
    return r;
  }

}
