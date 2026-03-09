// This is a generated file. Not intended for manual editing.
package com.aesirinteractive.angelscript.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.aesirinteractive.angelscript.AngelscriptTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.aesirinteractive.angelscript.psi.*;

public class AngelscriptStructDeclImpl extends ASTWrapperPsiElement implements AngelscriptStructDecl {

  public AngelscriptStructDeclImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull AngelscriptVisitor visitor) {
    visitor.visitStructDecl(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof AngelscriptVisitor) accept((AngelscriptVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<AngelscriptComment> getCommentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, AngelscriptComment.class);
  }

  @Override
  @NotNull
  public List<AngelscriptDefaultValueDecl> getDefaultValueDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, AngelscriptDefaultValueDecl.class);
  }

  @Override
  @NotNull
  public List<AngelscriptFunctionDecl> getFunctionDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, AngelscriptFunctionDecl.class);
  }

  @Override
  @Nullable
  public AngelscriptUStructDecl getUStructDecl() {
    return findChildByClass(AngelscriptUStructDecl.class);
  }

  @Override
  @NotNull
  public List<AngelscriptVariableDecl> getVariableDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, AngelscriptVariableDecl.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
