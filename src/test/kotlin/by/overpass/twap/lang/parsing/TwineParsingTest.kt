package by.overpass.twap.lang.parsing

import by.overpass.twap.Twine
import com.intellij.testFramework.ParsingTestCase

/**
 * TwineParsingTest
 */
class TwineParsingTest : ParsingTestCase(
    "",
    Twine.EXT,
    TwineParserDefinition()
) {

    fun testParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath() = "src/test/testData"

    override fun skipSpaces() = false

    override fun includeRanges() = true
}