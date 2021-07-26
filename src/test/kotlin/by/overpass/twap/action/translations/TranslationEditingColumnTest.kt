package by.overpass.twap.action.translations

import org.junit.Assert.assertTrue
import org.junit.Test

@Suppress("INLINE_CLASS_CAN_BE_USED")
class TranslationEditingColumnTest {

    private val translationEditingColumn = object : TranslationEditingColumn() {
        override fun valueOf(item: TranslationModel): String = ""
    }

    @Test
    fun testIsEditable() {
        assertTrue(translationEditingColumn.isCellEditable(TranslationModel("en", "english")))
    }
}