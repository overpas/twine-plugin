package by.overpass.twap.action.translations

/**
 * Model for a single translation
 *
 * @property locale the translation's locale
 * @property text the translation's text
 */
data class TranslationModel(
    var locale: String,
    var text: String
)
