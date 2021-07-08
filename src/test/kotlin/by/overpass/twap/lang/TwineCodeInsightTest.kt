package by.overpass.twap.lang

import by.overpass.twap.ServiceLocator
import by.overpass.twap.action.translations.EditTranslationsAction
import by.overpass.twap.action.translations.FakeDialogFactory
import by.overpass.twap.action.translations.FakeGradleSyncService
import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

/**
 * TwineCodeInsightTest
 */
class TwineCodeInsightTest : LightJavaCodeInsightFixtureTestCase() {

    /**
     * Get test data path
     */
    override fun getTestDataPath(): String = "src/test/testData"

    /**
     * Assert [by.overpass.twap.lang.reference.identifier.TwineIdentifierReference] works correctly
     */
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

    /**
     * Assert strings, java fields and twine label titles renamed
     */
    fun testRename() = with(myFixture) {
        configureByFiles("RenameTestData.java", "RenameTestData.twine", "before_rename_strings.xml")
        renameElementAtCaret("label2")
        checkResultByFile("RenameTestData.twine", "RenameTestDataAfter.twine", false)
        checkResultByFile("before_rename_strings.xml", "after_rename_strings.xml", false)
    }

    /**
     * Assert duplicate labels are annotated
     */
    fun testDuplicateLabelsAnnotator() = with(myFixture) {
        configureByFiles("AnnotatorTestData.twine")
        checkHighlighting(false, false, true, true)
        Unit
    }

    /**
     * Assert [EditTranslationsAction] is available
     */
    fun testEditTranslationsIntentionActionIsAvailable() = with(myFixture) {
        configureByFiles(EDIT_TRANSLATIONS_TEST_DATA_JAVA, EDIT_TRANSLATIONS_TEST_DATA_TWINE)
        val availableIntentionActions = getAvailableIntentions(EDIT_TRANSLATIONS_TEST_DATA_JAVA)
        assertEquals(EditTranslationsAction.NAME, availableIntentionActions[0].familyName)
    }

    /**
     * Assert [EditTranslationsAction] updates the translations in the twine file
     */
    fun testTranslationsEdited() = with(myFixture) {
        ServiceLocator.dialogFactoryField = FakeDialogFactory()
        ServiceLocator.gradleSyncServiceField = FakeGradleSyncService()
        configureByFiles(EDIT_TRANSLATIONS_TEST_DATA_JAVA, EDIT_TRANSLATIONS_TEST_DATA_TWINE)
        val availableIntentionActions = getAvailableIntentions(EDIT_TRANSLATIONS_TEST_DATA_JAVA)
        availableIntentionActions[0].invoke(project, editor, file)
        checkResultByFile(EDIT_TRANSLATIONS_TEST_DATA_TWINE, "After$EDIT_TRANSLATIONS_TEST_DATA.twine", false)
    }

    companion object {
        private const val EDIT_TRANSLATIONS_TEST_DATA = "EditTranslationsTestData"
        private const val EDIT_TRANSLATIONS_TEST_DATA_JAVA = "$EDIT_TRANSLATIONS_TEST_DATA.java"
        private const val EDIT_TRANSLATIONS_TEST_DATA_TWINE = "$EDIT_TRANSLATIONS_TEST_DATA.twine"
    }
}