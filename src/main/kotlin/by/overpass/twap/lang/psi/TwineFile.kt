package by.overpass.twap.lang.psi

import by.overpass.twap.lang.TwineFileType
import by.overpass.twap.lang.TwineLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class TwineFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, TwineLanguage) {

    override fun getFileType(): FileType = TwineFileType

    override fun toString(): String = "Twine File"
}