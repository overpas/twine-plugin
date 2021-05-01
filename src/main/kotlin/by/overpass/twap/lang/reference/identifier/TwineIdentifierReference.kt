package by.overpass.twap.lang.reference.identifier

import by.overpass.twap.lang.findTwineIds
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult

/**
 * Reference to [by.overpass.twap.lang.parsing.psi.TwineIdentifier] implementation
 */
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

    override fun handleElementRename(newElementName: String): PsiElement = super.handleElementRename(newElementName)
}