package by.overpass.twap.action

import by.overpass.twap.action.translations.TranslationsModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [TranslationsModel] test
 */
class TranslationsModelTest {
    /**
     * Assert translations updated
     */
    @Test
    fun `assert translations updated`() {
        val map: MutableMap<String, String> = mutableMapOf(
            "en" to "text",
            "ru" to "текст",
        )
        val translationsModel = TranslationsModel(map)
        translationsModel.setTranslation("en", "text2")

        assertEquals("text2", translationsModel.data["en"])
    }
}