package by.overpass.twap.action

import by.overpass.twap.action.translations.EditTranslationsDialog
import by.overpass.twap.action.translations.TranslationsModel
import by.overpass.twap.lang.parsing.TwineParserDefinition.Companion.rules
import by.overpass.twap.lang.parsing.psi.TwineLabel
import by.overpass.twap.parser.TwineParser
import com.intellij.testFramework.LightIdeaTestCase
import org.antlr.intellij.adaptor.psi.ANTLRPsiLeafNode
import org.junit.Test

class DialogFactoryTest : LightIdeaTestCase() {

    private val factory = DialogFactory()

    @Test
    fun testEditTranslationsDialogCreated() {
        val dialog = factory.createEditTranslationsDialog(
            project,
            TwineLabel(
                ANTLRPsiLeafNode(
                    rules[TwineParser.RULE_label],
                    """
                [label]
                    en = text
            """.trimIndent()
                )
            ),
            TranslationsModel(),
            "title"
        )
        assertTrue(dialog is EditTranslationsDialog)
        assertEquals(dialog.title, "title")
    }
}