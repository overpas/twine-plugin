package by.overpass.twap.action.translations

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [LocaleColumn] test
 */
class LocaleColumnTest {

    private val localeColumn = LocaleColumn()
    private val translationModel = TranslationModel("locale", "text")

    /**
     * Test that [translationModel] is updated
     */
    @Test
    fun testValueUpdated() {
        localeColumn.setValue(translationModel, "new locale")

        assertEquals("new locale", translationModel.locale)
    }

    /**
     * Test that locale is extracted from [translationModel]
     */
    @Test
    fun testLocaleFetched() {
        assertEquals("locale", localeColumn.valueOf(translationModel))
    }
}