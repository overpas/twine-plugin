package by.overpass.twap.lang

import by.overpass.twap.Twine
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.util.castSafelyTo


class TwineLineMarkerProvider : RelatedItemLineMarkerProvider() {

    private val androidStringResourceRegex = "R\\.string\\.(.+)".toRegex()

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>?>
    ) {
        element.takeIf { it is PsiIdentifier }
            ?.castSafelyTo<PsiIdentifier>()
            ?.takeIf { it.parent is PsiReferenceExpression }
            ?.parent
            ?.let { androidStringResourceRegex.find(it.text) }
            ?.groupValues
            ?.get(1)
            ?.let { element.project.findTwineIds(it) }
            ?.takeIf { it.isNotEmpty() }
            ?.let {
                val builder = NavigationGutterIconBuilder.create(Twine.ICON)
                    .setTargets(it)
                    .setTooltipText("Navigate to Twine label")
                result += builder.createLineMarkerInfo(element)
            }
    }
}