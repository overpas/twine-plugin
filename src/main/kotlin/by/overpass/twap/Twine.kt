package by.overpass.twap

import com.intellij.openapi.util.IconLoader

/**
 * Global constants and variables
 */
object Twine {
    const val LANGUAGE_ID = "Twine"
    const val EXT = "twine"
    val icon = IconLoader.getIcon("/icons/ic_twine.png", Twine.javaClass)
    val androidStringResourceRegex = "R\\.string\\.(.+)".toRegex()
}