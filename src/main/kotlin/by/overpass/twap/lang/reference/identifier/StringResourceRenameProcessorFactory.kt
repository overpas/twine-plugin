/**
 * RenameProcessor factory api and impl
 */

package by.overpass.twap.lang.reference.identifier

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.refactoring.rename.RenameProcessor

/**
 * Creates a [RenameProcessor]
 */
interface StringResourceRenameProcessorFactory {
    /**
     * Create a [RenameProcessor] to process of settings the name of [myPsiElement] to [newName]
     *
     * @param project the [Project]
     * @param myPsiElement the [PsiElement] that will be renamed
     * @param newName the new name
     * @return a [RenameProcessor] instance
     */
    fun create(
        project: Project,
        myPsiElement: PsiElement,
        newName: String
    ): RenameProcessor

    companion object {
        /**
         * Default implementation of [StringResourceRenameProcessorFactory]
         *
         * @return an instance of [StringResourceRenameProcessorFactory]
         */
        operator fun invoke(): StringResourceRenameProcessorFactory = DefaultStringResourceRenameProcessorFactory
    }
}

private object DefaultStringResourceRenameProcessorFactory : StringResourceRenameProcessorFactory {

    override fun create(
        project: Project,
        myPsiElement: PsiElement,
        newName: String
    ): RenameProcessor = RenameProcessor(
        project,
        myPsiElement,
        newName,
        GlobalSearchScope.projectScope(project),
        false,
        false
    )
}