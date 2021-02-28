package by.overpass.twap.lang

import by.overpass.twap.Twine
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceExpression


class TwineLineMarkerProvider : RelatedItemLineMarkerProvider() {

    private val androidStringResourceRegex = "R\\.string\\.(.+)".toRegex()

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>?>
    ) {
        if (element !is PsiReferenceExpression) {
            return
        }

        androidStringResourceRegex.find(element.text)
            ?.groupValues
            ?.get(1)
            ?.let { element.getProject().findTwineLabels(it) }
            ?.takeIf { it.isNotEmpty() }
            ?.let {
                val builder = NavigationGutterIconBuilder.create(Twine.ICON)
                    .setTargets(it)
                    .setTooltipText("Navigate to Twine label")
                result += builder.createLineMarkerInfo(element)
            }
    }
}