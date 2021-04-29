package by.overpass.twap.lang.parsing

import com.intellij.testFramework.ParsingTestCase

class TwineParsingTest : ParsingTestCase(
        "",
        "twine",
        TwineParserDefinition()) {

    fun testParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath() = "src/test/testData"

    override fun skipSpaces() = false

    override fun includeRanges() = true
}
