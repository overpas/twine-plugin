package by.overpass.twap.lang

import by.overpass.twap.Twine
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object TwineFileType : LanguageFileType(TwineLanguage) {

    override fun getName(): String {
        return "Twine"
    }

    override fun getDescription(): String {
        return "Twine file"
    }

    override fun getDefaultExtension(): String = Twine.EXT

    override fun getIcon(): Icon = Twine.ICON
}