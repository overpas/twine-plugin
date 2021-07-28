package by.overpass.twap.lang.parsing.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.ElementManipulator

/**
 * Handles value text change and provides its underlying TextRange for specific PsiElement.
 */
class TwineIdentifierManipulator : AbstractElementManipulator<TwineIdentifier>(),
    ElementManipulator<TwineIdentifier> {

    override fun handleContentChange(
        element: TwineIdentifier,
        range: TextRange,
        newContent: String?,
    ): TwineIdentifier {
        newContent?.let {
            return element.replace(element.project.createTwineIdentifier(newContent)) as TwineIdentifier
        }
        return element
    }
}