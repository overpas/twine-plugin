package by.overpass.twap.lang.parsing.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

/**
 * Creates [PsiElement]s from [ASTNode]s
 */
interface PsiElementFactory {
    /**
     * @param node
     * @return Twine [PsiElement]
     */
    fun createElement(node: ASTNode): PsiElement
}
