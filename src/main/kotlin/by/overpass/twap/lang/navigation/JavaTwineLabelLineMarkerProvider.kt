package by.overpass.twap.lang.navigation

import by.overpass.twap.Twine
import by.overpass.twap.lang.reference.identifier.underscoreReplacement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.util.castSafelyTo

/**
 * Provides line marker navigation to corresponding [by.overpass.twap.lang.parsing.psi.TwineIdentifier]s from Java
 */
class JavaTwineLabelLineMarkerProvider : TwineLabelLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: NavMarkersResultCollection,
    ) {
        element.takeIf { it is PsiIdentifier }
            ?.castSafelyTo<PsiIdentifier>()
            ?.takeIf { it.parent is PsiReferenceExpression }
            ?.parent
            ?.let { Twine.androidStringResourceRegex.find(it.text) }
            ?.groupValues
            ?.get(1)
            ?.run(underscoreReplacement)
            ?.let { collectTwineIdRefs(element, result, it) }
    }
}