package by.overpass.twap.action.translations

import org.junit.Assert.assertEquals
import org.junit.Test

class TranslationColumnTest {

    private val translationColumn = TranslationColumn()
    private val translationModel = TranslationModel("locale", "text")

    @Test
    fun testValueUpdated() {
        translationColumn.setValue(translationModel, "new text")

        assertEquals("new text", translationModel.text)
    }

    @Test
    fun testTextFetched() {
        assertEquals("text", translationColumn.valueOf(translationModel))
    }
}