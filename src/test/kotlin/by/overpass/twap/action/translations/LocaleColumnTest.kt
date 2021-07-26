package by.overpass.twap.action.translations

import org.junit.Assert.assertEquals
import org.junit.Test

class LocaleColumnTest {

    private val localeColumn = LocaleColumn()
    private val translationModel = TranslationModel("locale", "text")

    @Test
    fun testValueUpdated() {
        localeColumn.setValue(translationModel, "new locale")

        assertEquals("new locale", translationModel.locale)
    }

    @Test
    fun testLocaleFetched() {
        assertEquals("locale", localeColumn.valueOf(translationModel))
    }
}