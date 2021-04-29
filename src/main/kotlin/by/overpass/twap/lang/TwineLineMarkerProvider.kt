/**
 * LineMarkerProvider implementation
 */

package by.overpass.twap.lang

import by.overpass.twap.Twine
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.util.castSafelyTo

typealias NavigationMarkersResultCollection = MutableCollection<in RelatedItemLineMarkerInfo<*>?>

/**
 * Provides line marker navigation to twine labels
 */
class TwineLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: NavigationMarkersResultCollection
    ) {
        element
            .takeIf { it is PsiIdentifier }
            ?.castSafelyTo<PsiIdentifier>()
            ?.takeIf { it.parent is PsiReferenceExpression }
            ?.parent
            ?.let { Twine.androidStringResourceRegex.find(it.text) }
            ?.groupValues
            ?.get(1)
            ?.let { element.project.findTwineIds(it) }
            ?.takeIf { it.isNotEmpty() }
            ?.let {
                val builder = NavigationGutterIconBuilder
                    .create(Twine.icon)
                    .setTargets(it)
                    .setTooltipText("Navigate to Twine label")
                result += builder.createLineMarkerInfo(element)
            }
    }
}
