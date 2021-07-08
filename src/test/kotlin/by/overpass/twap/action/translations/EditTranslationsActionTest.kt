package by.overpass.twap.action.translations

import by.overpass.twap.ServiceLocator
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

/**
 * [EditTranslationsAction] test
 */
class EditTranslationsActionTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "src/test/testData"

    /**
     * Assert [EditTranslationsAction] is available
     */
    fun testEditTranslationsIntentionActionIsAvailable() = with(myFixture) {
        configureByFiles("$EDIT_TRANSLATIONS_TEST_DATA.java", "$EDIT_TRANSLATIONS_TEST_DATA.twine")
        val availableIntentionActions = getAvailableIntentions("$EDIT_TRANSLATIONS_TEST_DATA.java")
        assertEquals(EditTranslationsAction.NAME, availableIntentionActions[0].familyName)
    }

    /**
     * Assert [EditTranslationsAction] updates the translations in the twine file
     */
    fun testTranslationsEdited() = with(myFixture) {
        ServiceLocator.dialogFactoryField = FakeDialogFactory()
        ServiceLocator.gradleSyncServiceField = FakeGradleSyncService()
        configureByFiles("$EDIT_TRANSLATIONS_TEST_DATA.java", "$EDIT_TRANSLATIONS_TEST_DATA.twine")
        val availableIntentionActions = getAvailableIntentions("$EDIT_TRANSLATIONS_TEST_DATA.java")
        availableIntentionActions[0].invoke(project, editor, file)
        checkResultByFile("$EDIT_TRANSLATIONS_TEST_DATA.twine", "After$EDIT_TRANSLATIONS_TEST_DATA.twine", false)
    }

    companion object {
        private const val EDIT_TRANSLATIONS_TEST_DATA = "EditTranslationsTestData"
    }
}