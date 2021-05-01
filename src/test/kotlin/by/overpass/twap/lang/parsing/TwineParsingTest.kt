package by.overpass.twap.lang.parsing

import by.overpass.twap.Twine
import com.intellij.testFramework.ParsingTestCase


class TwineParsingTest : ParsingTestCase("", Twine.EXT, TwineParserDefinition()) {

    fun testParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath(): String? {
        return "src/test/testData"
    }

    override fun skipSpaces(): Boolean {
        return false
    }

    override fun includeRanges(): Boolean {
        return true
    }
}