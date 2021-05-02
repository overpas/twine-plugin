package by.overpass.twap.parser

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * VocabularyKtTest
 */
class VocabularyKtTest {

    @Test
    fun testTokenNames() {
        val expected = TwineParser.tokenNames
        val actual = TwineParser.VOCABULARY.tokenNames

        assertEquals(expected.size, actual.size)
        expected.forEachIndexed { index, it ->
            assertEquals(it, actual[index])
        }
    }
}