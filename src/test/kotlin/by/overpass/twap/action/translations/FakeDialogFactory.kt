package by.overpass.twap.action.translations

import by.overpass.twap.action.DialogFactory
import by.overpass.twap.lang.parsing.psi.TwineLabel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Fake implementation if [DialogFactory] to be used in [EditTranslationsActionTest]
 */
class FakeDialogFactory : DialogFactory {

    override fun createEditTranslationsDialog(
        project: Project,
        twineLabel: TwineLabel,
        translationsModel: TranslationsModel,
        title: String
    ): DialogWrapper = object : DialogWrapper(project) {

        override fun createCenterPanel(): JComponent = JPanel()

        override fun showAndGet(): Boolean {
            translationsModel.apply {
                setTranslation("en", "english")
                setTranslation("ru", "russian")
            }
            return true
        }

        override fun isModal(): Boolean = true
    }
}