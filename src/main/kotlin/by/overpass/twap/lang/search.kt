package by.overpass.twap.lang

import by.overpass.twap.lang.psi.TwineFile
import by.overpass.twap.lang.psi.TwineParserDefinition
import by.overpass.twap.parser.TwineLexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType


fun Project.findTwineLabels(id: String): List<PsiElement> {
    val result = mutableListOf<PsiElement>()
    val virtualFiles = FileTypeIndex.getFiles(TwineFileType, GlobalSearchScope.allScope(this))
    for (virtualFile in virtualFiles) {
        val twineFile = PsiManager.getInstance(this).findFile(virtualFile) as TwineFile?
        if (twineFile != null) {
            val properties = PsiTreeUtil.getChildrenOfType(twineFile, PsiElement::class.java)
                ?.filter { it.elementType?.equals(TwineParserDefinition.Elements[TwineLexer.ID]) ?: false }
            if (properties != null) {
                for (property in properties) {
                    if (id == property.text) {
                        result.add(property)
                    }
                }
            }
        }
    }
    return result
}