/**
 * Twine elements search utilities
 */

package by.overpass.twap.lang

import by.overpass.twap.lang.parsing.TwineFile
import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil

/**
 * @param id
 * @return all [TwineIdentifier]s with [id] in [Project]
 */
fun Project.findTwineIds(id: String): List<TwineIdentifier> = findTwineIds().filter { id == it.text }

/**
 * @return all [TwineIdentifier]s in [Project]
 */
fun Project.findTwineIds(): List<TwineIdentifier> = findTwineElements().filterIsInstance<TwineIdentifier>()

/**
 * @return all twine psi elements in [Project]
 */
fun Project.findTwineElements(): List<PsiElement> =
    FileTypeIndex.getFiles(TwineFileType, GlobalSearchScope.allScope(this))
        .mapNotNull { PsiManager.getInstance(this).findFile(it) }
        .map { it as TwineFile }
        .fold(mutableListOf()) { acc, twineFile ->
            acc += PsiTreeUtil.collectElements(twineFile) { true }
            acc
        }
