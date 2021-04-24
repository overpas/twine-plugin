package by.overpass.twap.lang

import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import org.junit.Test

class TwineCodeInsightTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "src/test/testData"

    fun testReferences() = with(myFixture) {
        configureByFiles("ReferencesTestData.java", "ReferencesTestData.twine")
        file.findElementAt(myFixture.caretOffset)!!
            .parent
            .run { references[0] }
            .run { resolve() }
            .let { it as TwineIdentifier }
            .let {
                assertEquals("label1", it.name)
            }
    }

    fun testRename() = with(myFixture) {
        configureByFiles("RenameTestData.java", "RenameTestData.twine")
        renameElementAtCaret("label2")
        checkResultByFile("RenameTestData.twine", "RenameTestDataAfter.twine", false)
    }

    @Test
    fun testDuplicateLabelsAnnotator() = with(myFixture) {
        configureByFiles("AnnotatorTestData.twine")
        checkHighlighting(false, false, true, true)
        Unit
    }
}