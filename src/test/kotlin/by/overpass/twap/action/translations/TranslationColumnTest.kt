package by.overpass.twap.action.translations

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [TranslationColumn] test
 */
class TranslationColumnTest {

    private val translationColumn = TranslationColumn()
    private val translationModel = TranslationModel("locale", "text")

    /**
     * Test that [translationModel] is updated with new text
     */
    @Test
    fun testValueUpdated() {
        translationColumn.setValue(translationModel, "new text")

        assertEquals("new text", translationModel.text)
    }

    /**
     * Test that text is extracted from [translationModel]
     */
    @Test
    fun testTextFetched() {
        assertEquals("text", translationColumn.valueOf(translationModel))
    }
}