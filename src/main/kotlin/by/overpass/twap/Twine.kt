package by.overpass.twap

import com.intellij.openapi.util.IconLoader

object Twine {
    const val LANGUAGE_ID = "Twine"
    const val EXT = "twine"
    val ICON = IconLoader.getIcon("/icons/ic_twine.png", Twine.javaClass)
    val ANDROID_STRING_RESOURCE_REGEX = "R\\.string\\.(.+)".toRegex()
}