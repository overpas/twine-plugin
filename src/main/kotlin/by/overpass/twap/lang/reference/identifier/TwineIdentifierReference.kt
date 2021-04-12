package by.overpass.twap.lang.reference.identifier

import by.overpass.twap.lang.findTwineIds
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*

class TwineIdentifierReference(
    element: PsiElement,
    textRange: TextRange
) : PsiPolyVariantReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val id: String by lazy {
        element.text.substring(textRange.startOffset, textRange.endOffset)
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> = myElement.project
        .findTwineIds(id)
        .map { PsiElementResolveResult(it) }
        .toTypedArray()

    override fun handleElementRename(newElementName: String): PsiElement {
        return super.handleElementRename(newElementName)
    }
}