// This is a generated file. Not intended for manual editing.
package com.aesirinteractive.angelscript.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface AngelscriptVariableDecl extends PsiElement {

  @NotNull
  List<AngelscriptArgList> getArgListList();

  @NotNull
  List<AngelscriptExpr> getExprList();

  @NotNull
  AngelscriptTypeRef getTypeRef();

  @Nullable
  AngelscriptUPropertyDecl getUPropertyDecl();

}
