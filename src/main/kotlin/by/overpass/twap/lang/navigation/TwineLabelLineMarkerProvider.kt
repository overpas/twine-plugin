/**
 * Line Marker Provider for navigation to Twine labels
 */

package by.overpass.twap.lang.navigation

import by.overpass.twap.Twine
import by.overpass.twap.lang.findTwineIds
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement

typealias NavMarkersResultCollection = MutableCollection<in RelatedItemLineMarkerInfo<*>?>

/**
 * Provides line marker navigation to corresponding [by.overpass.twap.lang.parsing.psi.TwineIdentifier]s
 */
open class TwineLabelLineMarkerProvider : RelatedItemLineMarkerProvider() {
    /**
     * @param element currently processed [PsiElement]
     * @param result result nav markers collection
     * @param id sought twine label id
     */
    protected fun collectTwineIdRefs(
        element: PsiElement,
        result: NavMarkersResultCollection,
        id: String,
    ) {
        element.project
            .findTwineIds(id)
            .takeIf { it.isNotEmpty() }
            ?.let {
                val builder = NavigationGutterIconBuilder.create(Twine.icon)
                    .setTargets(it)
                    .setTooltipText(MESSAGE_NAVIGATE_TO_TWINE_LABEL)
                result += builder.createLineMarkerInfo(element)
            }
    }

    companion object {
        const val MESSAGE_NAVIGATE_TO_TWINE_LABEL = "Navigate to Twine label"
    }
}