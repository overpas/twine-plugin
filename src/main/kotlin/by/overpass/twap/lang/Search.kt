/**
 * Twine elements search utilities
 */

package by.overpass.twap.lang

import by.overpass.twap.lang.parsing.TwineFile
import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.PsiTreeUtil

val Project.allScope: GlobalSearchScope
    get() = GlobalSearchScope.allScope(this)

val Project.projectScope: GlobalSearchScope
    get() = ProjectScope.getProjectScope(this)

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
fun Project.findTwineElements(): List<PsiElement> = findFiles(TwineFileType, allScope)
    .map { it as TwineFile }
    .fold(mutableListOf()) { acc, twineFile ->
        acc += PsiTreeUtil.collectElements(twineFile) { true }
        acc
    }

/**
 * @return Java files in [Project]
 */
fun Project.findJavaFiles(): List<PsiFile> = findFiles(JavaFileType.INSTANCE)

/**
 * @return files with [fileType] in [Project] withing [scope]
 */
fun Project.findFiles(
    fileType: FileType,
    scope: GlobalSearchScope = projectScope
): List<PsiFile> =
    FileTypeIndex.getFiles(fileType, scope)
        .mapNotNull { PsiManager.getInstance(this).findFile(it) }
