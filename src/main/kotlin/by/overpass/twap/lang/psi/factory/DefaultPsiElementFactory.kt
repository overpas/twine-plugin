package by.overpass.twap.lang.psi.factory

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

object DefaultPsiElementFactory : PsiElementFactory {

    override fun createElement(node: ASTNode): PsiElement {
        return ANTLRPsiNode(node)
    }
}