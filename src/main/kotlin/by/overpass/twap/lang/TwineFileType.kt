package by.overpass.twap.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object TwineFileType : LanguageFileType(TwineLanguage) {

    override fun getName(): String {
        return "Twine File"
    }

    override fun getDescription(): String {
        return "Twine file"
    }

    override fun getDefaultExtension(): String = "twine"

    override fun getIcon(): Icon = twineIcon
}