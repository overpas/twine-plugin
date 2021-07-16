package by.overpass.twap.action.translations

/**
 * Translations model
 */
class TranslationsModel(private val keyValues: MutableMap<String, String> = mutableMapOf()) {
    /**
     * Translations model map representation (locale to text)
     */
    val data: Map<String, String> = keyValues

    /**
     * @param locale translation's locale
     * @param text translation's text
     */
    fun setTranslation(locale: String, text: String) {
        keyValues[locale] = text
    }
}