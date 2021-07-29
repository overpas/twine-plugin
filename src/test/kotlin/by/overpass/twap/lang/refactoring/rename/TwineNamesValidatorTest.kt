package by.overpass.twap.lang.refactoring.rename

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [TwineNamesValidator]
 */
@Suppress("INLINE_CLASS_CAN_BE_USED")
class TwineNamesValidatorTest {

    private val twineNamesValidator = TwineNamesValidator()

    /**
     * Test that none of the strings are twine keywords
     */
    @Test
    fun `nothing is a keyword`() {
        listOf("", "dfdfh", "12", "[><]").forEach {
            assertFalse("keyword = $it", twineNamesValidator.isKeyword(it, null))
        }
    }

    /**
     * Test that all of the strings valid twine identifiers
     */
    @Test
    fun `valid identifiers`() {
        listOf(
            "dfd",
            "jhbhj.hbh",
            "jhbhj.hbh.dfbdb",
            "jhbhj.hBB",
            "jhbhj.hBB.dfdfdf",
            "jhbhj.hBB.dfdfdf.dFf",
            "jhbhj.hBB.dfdfdf.dFF",
            "jhbhj.hbh.a12",
            "dfv.a12.kmkdf",
            "dfv.a12.kmkdf._1",
        ).forEach {
            assertTrue("identifier = $it", twineNamesValidator.isIdentifier(it, null))
        }
    }

    /**
     * Test that all of the strings are invalid twine identifiers
     */
    @Test
    fun `invalid identifiers`() {
        listOf(
            "",
            "jhbhj.",
            "jhbhj..",
            "jhbhj.bb  df.vfv",
            ".jhbhj",
        ).forEach {
            assertFalse("identifier = $it", twineNamesValidator.isIdentifier(it, null))
        }
    }
}