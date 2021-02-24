package by.overpass.twap.lang.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

object DefaultPsiElementFactory : PsiElementFactory {

    override fun createElement(node: ASTNode): PsiElement {
        return TwinePsiElement(node)
    }
}