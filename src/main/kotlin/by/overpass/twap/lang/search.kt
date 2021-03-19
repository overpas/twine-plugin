package by.overpass.twap.lang

import by.overpass.twap.lang.parsing.TwineFile
import by.overpass.twap.lang.parsing.TwineParserDefinition
import by.overpass.twap.parser.TwineLexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType


fun Project.findTwineIds(id: String): List<PsiElement> = findTwineIds().filter { id == it.text }

fun Project.findTwineIds(): List<PsiElement> = findTwineElements().filter {
    it.elementType
        ?.equals(TwineParserDefinition.Elements[TwineLexer.ID])
        ?: false
}

fun Project.findTwineElements(): List<PsiElement> =
    FileTypeIndex.getFiles(TwineFileType, GlobalSearchScope.allScope(this))
        .mapNotNull { PsiManager.getInstance(this).findFile(it) }
        .map { it as TwineFile }
        .flatMap { PsiTreeUtil.getChildrenOfType(it, PsiElement::class.java).asSequence() }
        .toList()
