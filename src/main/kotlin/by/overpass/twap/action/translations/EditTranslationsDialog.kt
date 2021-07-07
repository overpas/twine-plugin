/**
 * Edit translation action UI
 */

package by.overpass.twap.action.translations

import by.overpass.twap.lang.parsing.psi.TwineLabel
import by.overpass.twap.lang.parsing.psi.TwineTranslation
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBTextField
import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

private typealias OnTextChanged = (locale: String, text: String) -> Unit

private const val ADD_BUTTON_WIDTH = 40
private const val ADD_BUTTON_HEIGHT = 20

private val styling: JButton.() -> Unit = {
    isBorderPainted = false
    isOpaque = false
    maximumSize = Dimension(ADD_BUTTON_WIDTH, ADD_BUTTON_HEIGHT)
}

/**
 * Shows UI for editing translations
 */
class EditTranslationsDialog(
    project: Project,
    private val twineLabel: TwineLabel,
    private val translationsModel: TranslationsModel
) : DialogWrapper(project) {

    init {
        init()
    }

    override fun createCenterPanel(): JComponent = createEditTranslationsPanel(
        twineLabel.translations,
        translationsModel
    )
}

/**
 * Add a listener that is updated when [JBTextField] is updated
 *
 * @param onTextChange triggered when text is updated
 */
fun JBTextField.addOnTextChangeListener(onTextChange: (text: String) -> Unit) {
    document.addDocumentListener(object : DocumentListener {
        override fun insertUpdate(event: DocumentEvent) {
            onTextChange(text)
        }

        override fun removeUpdate(event: DocumentEvent) {
            onTextChange(text)
        }

        override fun changedUpdate(event: DocumentEvent) {
            onTextChange(text)
        }
    })
}

/**
 * Creates edit translation UI
 *
 * @param translations collection of [TwineTranslation]s
 * @param translationsModel model for translations editing
 * @return an instance of [JComponent] for translations editing UI
 */
fun createEditTranslationsPanel(
    translations: List<TwineTranslation>,
    translationsModel: TranslationsModel
): JComponent = JPanel().apply {
    layout = BoxLayout(this, BoxLayout.Y_AXIS)
    translations.forEach { translation ->
        val translationBox = createTranslationBox(
            translation.localeValue,
            translation.textValue
        ) { locale, text ->
            translationsModel.setTranslation(locale, text)
        }
        add(translationBox)
    }
    val button = JButton("+")
    button.apply(styling)
    button.addActionListener {
        remove(button)
        val translationBox = createTranslationBox(
            "",
            ""
        ) { locale, text ->
            translationsModel.setTranslation(locale, text)
        }
        add(translationBox)
        add(button)
        revalidate()
    }
    add(button)
}

/**
 * Creates a box for single translation
 *
 * @param locale locale of the translation
 * @param translation text of the translation
 * @param onTranslationChanged triggered when translation is updated
 * @return an instance of [JBBox] for a single translation
 */
fun createTranslationBox(
    locale: String,
    translation: String,
    onTranslationChanged: OnTextChanged,
): JBBox {
    val box = JBBox(BoxLayout.X_AXIS)
    val localeTextField = JBTextField(locale)
    val translationTextField = JBTextField(translation)
    box.add(localeTextField)
    box.add(translationTextField)
    translationTextField.addOnTextChangeListener {
        onTranslationChanged(localeTextField.text, it)
    }
    return box
}