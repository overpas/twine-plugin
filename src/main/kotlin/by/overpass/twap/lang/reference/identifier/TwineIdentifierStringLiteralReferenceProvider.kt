package by.overpass.twap.lang.reference.identifier

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext
import com.intellij.util.castSafelyTo

// TODO: This needs to be changed, we don't really need to reference twine elements in string literals,
//  it's just an example
class TwineIdentifierStringLiteralReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(
        element: PsiElement,
        context: ProcessingContext
    ): Array<PsiReference> = element.castSafelyTo<PsiLiteralExpression>()
        ?.value
        ?.takeIf { it is String }
        ?.let { it as String }
        ?.takeIf { it.startsWith(TWINE_PREFIX) }
        ?.let {
            val textRange = TextRange(
                TWINE_PREFIX.length + 1,
                it.length + 1
            )
            arrayOf(TwineIdentifierReference(element, textRange))
        }
        ?: PsiReference.EMPTY_ARRAY

    companion object {
        private const val TWINE_PREFIX = "twine:"
    }
}