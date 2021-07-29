/**
 * Dialog creation
 */

package by.overpass.twap.action

import by.overpass.twap.action.translations.EditTranslationsDialog
import by.overpass.twap.action.translations.TranslationsModel
import by.overpass.twap.lang.parsing.psi.TwineLabel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper

/**
 * Creates dialog UIs
 */
interface DialogFactory {
    /**
     * @param project current [Project]
     * @param twineLabel twine label that is being edited
     * @param translationsModel model for edited translations
     * @param title dialog title
     * @return dialog for editing translations
     */
    fun createEditTranslationsDialog(
        project: Project,
        twineLabel: TwineLabel,
        translationsModel: TranslationsModel,
        title: String,
    ): DialogWrapper

    companion object {
        /**
         * @return the default [DialogFactory] implementation
         */
        operator fun invoke(): DialogFactory = DefaultDialogFactory
    }
}

private object DefaultDialogFactory : DialogFactory {

    override fun createEditTranslationsDialog(
        project: Project,
        twineLabel: TwineLabel,
        translationsModel: TranslationsModel,
        title: String,
    ): DialogWrapper = EditTranslationsDialog(project, twineLabel, translationsModel).apply {
        this.title = title
    }
}