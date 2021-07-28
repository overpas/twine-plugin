package by.overpass.twap.lang.refactoring.rename

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TwineNamesValidatorTest {

    private val twineNamesValidator = TwineNamesValidator()

    @Test
    fun `nothing is a keyword`() {
        listOf("", "dfdfh", "12", "[><]").forEach {
            assertFalse(twineNamesValidator.isKeyword(it, null))
        }
    }

    @Test
    fun `valid identifiers`() {
        listOf(/*"dfd", "jhbhj.hbh", */"dfv.a12.kmKdf").forEach {
            assertTrue(twineNamesValidator.isIdentifier(it, null))
        }
    }
}