package by.overpass.twap.lang.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

interface PsiElementFactory {
    fun createElement(node: ASTNode): PsiElement
}