package by.overpass.twap.lang.parsing

import by.overpass.twap.lang.TwineFileType
import by.overpass.twap.lang.TwineLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

/**
 * Twine file
 */
class TwineFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, TwineLanguage) {

    override fun getFileType(): FileType = TwineFileType

    override fun toString(): String = "Twine File"
}