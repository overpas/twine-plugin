package by.overpass.twap.lang.navigation

import by.overpass.twap.Twine
import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlToken
import com.intellij.psi.xml.XmlTokenType
import com.intellij.util.castSafelyTo

/**
 * Provides line marker navigation to corresponding [by.overpass.twap.lang.parsing.psi.TwineIdentifier]s from XML
 */
class XmlTwineLabelLineMarkerProvider : TwineLabelLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: NavMarkersResultCollection,
    ) {
        element.takeIf { it is XmlToken }
            ?.castSafelyTo<XmlToken>()
            ?.takeIf { it.tokenType == XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN }
            ?.let { Twine.androidStringXmlReferenceRegex.find(it.text) }
            ?.groupValues
            ?.get(1)
            ?.let { collectTwineIdRefs(element, result, it) }
    }
}