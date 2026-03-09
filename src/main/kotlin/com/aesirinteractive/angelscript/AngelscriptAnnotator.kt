package com.aesirinteractive.angelscript

import com.aesirinteractive.angelscript.psi.AngelscriptArgList
import com.aesirinteractive.angelscript.psi.AngelscriptClassDecl
import com.aesirinteractive.angelscript.psi.AngelscriptFunctionDecl
import com.aesirinteractive.angelscript.psi.AngelscriptParameterDecl
import com.aesirinteractive.angelscript.psi.AngelscriptPostfixExpr
import com.aesirinteractive.angelscript.psi.AngelscriptScopeRef
import com.aesirinteractive.angelscript.psi.AngelscriptStructDecl
import com.aesirinteractive.angelscript.psi.AngelscriptTypeArgument
import com.aesirinteractive.angelscript.psi.AngelscriptTypeRef
import com.aesirinteractive.angelscript.psi.AngelscriptVariableAccessExpr
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType

class AngelscriptAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is AngelscriptFunctionDecl       -> annotateFunctionDecl(element, holder)
            is AngelscriptClassDecl          -> annotateNamedDecl(element, holder)
            is AngelscriptStructDecl         -> annotateNamedDecl(element, holder)
            is AngelscriptParameterDecl      -> highlight(element.identifier, holder, AngelscriptSyntaxHighlighter.PARAMETER_KEY)
            is AngelscriptTypeRef            -> annotateTypeRef(element, holder)
            is AngelscriptVariableAccessExpr -> annotateVariableAccessExpr(element, holder)
            is AngelscriptPostfixExpr        -> annotatePostfixFieldAccess(element, holder)
        }
    }

    private fun highlight(element: PsiElement, holder: AnnotationHolder, key: TextAttributesKey) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .textAttributes(key)
            .create()
    }

    private fun annotateFunctionDecl(decl: AngelscriptFunctionDecl, holder: AnnotationHolder) {
        highlight(decl.identifier, holder, AngelscriptSyntaxHighlighter.FUNCTION_DECLARATION_KEY)
    }

    // ClassDecl / StructDecl: the class/struct name is the first IDENTIFIER child
    private fun annotateNamedDecl(element: PsiElement, holder: AnnotationHolder) {
        val nameNode = element.node.findChildByType(AngelscriptTypes.IDENTIFIER) ?: return
        highlight(nameNode.psi, holder, typeKeyFor(nameNode.text))
    }

    private fun annotateTypeRef(typeRef: AngelscriptTypeRef, holder: AnnotationHolder) {
        // DataType is a private rule — its IDENTIFIER token appears as a direct child of TypeRef.
        // Skip leading CONST token and any ScopeRef children (which are namespace qualifiers).
        for (child in typeRef.children) {
            if (child is AngelscriptScopeRef) continue
            if (child.node.elementType == AngelscriptTypes.CONST) continue
            if (child.node.elementType == AngelscriptTypes.IDENTIFIER) {
                highlight(child, holder, typeKeyFor(child.text))
                break
            }
            // Once we encounter something else (e.g. PrimitiveType keyword, '?', 'auto'),
            // stop — no identifier to highlight.
            break
        }
    }

    private fun typeKeyFor(name: String): TextAttributesKey {
        if (name.length >= 2 && name[1].isUpperCase() && name[0] in "UAFET") {
            return AngelscriptSyntaxHighlighter.UNREAL_TYPE_KEY
        }
        return AngelscriptSyntaxHighlighter.TYPE_REF_KEY
    }

    // VariableAccessExpr: function call if parent PostfixExpr has ArgList, otherwise check Unreal type pattern
    private fun annotateVariableAccessExpr(expr: AngelscriptVariableAccessExpr, holder: AnnotationHolder) {
        val parent = expr.parent
        if (parent is AngelscriptPostfixExpr && parent.argListList.isNotEmpty()) {
            highlight(expr.identifier, holder, AngelscriptSyntaxHighlighter.FUNCTION_CALL_KEY)
        } else {
            val key = typeKeyFor(expr.identifier.text)
            if (key != AngelscriptSyntaxHighlighter.TYPE_REF_KEY) {
                highlight(expr.identifier, holder, key)
            }
        }
    }

    // DOT IDENTIFIER pairs inside PostfixExpr = field or method access
    private fun annotatePostfixFieldAccess(expr: AngelscriptPostfixExpr, holder: AnnotationHolder) {
        var prevWasDot = false
        for (child in expr.node.getChildren(null)) {
            when {
                child.elementType == AngelscriptTypes.DOT -> prevWasDot = true
                prevWasDot && child.elementType == AngelscriptTypes.IDENTIFIER -> {
                    // Check next sibling to distinguish method call from field access
                    val next = child.treeNext
                    val isCall = next?.elementType == AngelscriptTypes.LPAREN
                              || next?.psi is AngelscriptTypeArgument
                              || next?.psi is AngelscriptArgList
                    highlight(
                        child.psi,
                        holder,
                        if (isCall) AngelscriptSyntaxHighlighter.FUNCTION_CALL_KEY
                        else AngelscriptSyntaxHighlighter.INSTANCE_FIELD_KEY
                    )
                    prevWasDot = false
                }
                child.elementType == TokenType.WHITE_SPACE -> { /* skip whitespace */ }
                else -> prevWasDot = false
            }
        }
    }
}
