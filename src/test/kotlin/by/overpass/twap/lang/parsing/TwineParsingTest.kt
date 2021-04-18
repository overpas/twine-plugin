package by.overpass.twap.lang.parsing

import com.intellij.testFramework.ParsingTestCase


class TwineParsingTest : ParsingTestCase("", "twine", TwineParserDefinition()) {

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