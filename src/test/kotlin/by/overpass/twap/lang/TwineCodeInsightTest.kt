package by.overpass.twap.lang

import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

class TwineCodeInsightTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "src/test/testData"

    fun testReferences() {
        myFixture.configureByFiles("ReferencesTestData.java", "ReferencesTestData.twine")
        myFixture.file
            .findElementAt(myFixture.caretOffset)!!
            .parent
            .run { references[0] }
            .run { resolve() }
            .let { it as TwineIdentifier }
            .let {
                assertEquals("label1", it.name)
            }
    }
}