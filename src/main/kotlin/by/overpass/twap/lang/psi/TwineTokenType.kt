package by.overpass.twap.lang.psi

import by.overpass.twap.lang.TwineLanguage
import com.intellij.psi.tree.IElementType

class TwineTokenType(debugName: String) : IElementType(debugName, TwineLanguage) {

    override fun toString(): String = "TwineTokenType.${super.toString()}"
}