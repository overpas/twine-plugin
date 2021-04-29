package by.overpass.twap.lang

import by.overpass.twap.Twine
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object TwineFileType : LanguageFileType(TwineLanguage) {

    override fun getName() = "Twine"

    override fun getDescription() = "Twine file"

    override fun getDefaultExtension(): String = Twine.EXT

    override fun getIcon(): Icon = Twine.icon
}
