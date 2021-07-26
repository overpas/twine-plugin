/**
 * Edit translation action UI
 */

package by.overpass.twap.action.translations

import by.overpass.twap.lang.parsing.psi.TwineLabel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
import javax.swing.JComponent

/**
 * Shows UI for editing translations
 */
class EditTranslationsDialog(
    project: Project,
    twineLabel: TwineLabel,
    private val translationsModel: TranslationsModel
) : DialogWrapper(project) {

    private val tableModel: ListTableModel<TranslationModel> = ListTableModel(
        arrayOf(LocaleColumn(), TranslationColumn()),
        twineLabel.translations.map { TranslationModel(it.localeValue, it.textValue) },
    )

    init {
        init()
    }

    override fun createCenterPanel(): JComponent = createTranslationsTable(tableModel)

    override fun showAndGet(): Boolean {
        val showAndGet = super.showAndGet()
        tableModel.items.forEach {
            translationsModel.setTranslation(it.locale, it.text)
        }
        return showAndGet
    }
}

/**
 * Base translation editing column
 */
abstract class TranslationEditingColumn : ColumnInfo<TranslationModel, String>("") {

    override fun isCellEditable(item: TranslationModel): Boolean = true
}

/**
 * Locale editing column
 */
class LocaleColumn : TranslationEditingColumn() {

    override fun valueOf(item: TranslationModel): String = item.locale

    override fun setValue(item: TranslationModel, value: String) {
        item.locale = value
    }
}

/**
 * Translation editing column
 */
class TranslationColumn : TranslationEditingColumn() {

    override fun valueOf(item: TranslationModel): String = item.text

    override fun setValue(item: TranslationModel, value: String) {
        item.text = value
    }
}

/**
 * @param tableModel table UI model
 * @return translations table UI
 */
fun createTranslationsTable(
    tableModel: ListTableModel<TranslationModel>,
): JComponent = ToolbarDecorator.createDecorator(TableView(tableModel))
    .disableUpAction()
    .disableDownAction()
    .setAddAction {
        tableModel.addRow(TranslationModel("", ""))
    }
    .setRemoveAction {
        if (tableModel.rowCount > 1) {
            tableModel.removeRow(tableModel.rowCount - 1)
        }
    }
    .createPanel()
