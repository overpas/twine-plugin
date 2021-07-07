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
        configureByFiles("EditTranslationsTestData.java", "EditTranslationsTestData.twine")
        val availableIntentionActions = getAvailableIntentions("EditTranslationsTestData.java")
        assertEquals(EditTranslationsAction.NAME, availableIntentionActions[0].familyName)
    }

    /**
     * Assert [EditTranslationsAction] updates the translations in the twine file
     */
    fun testTranslationsEdited() = with(myFixture) {
        ServiceLocator.dialogFactoryField = FakeDialogFactory()
        ServiceLocator.gradleSyncServiceField = FakeGradleSyncService()
        configureByFiles("EditTranslationsTestData.java", "EditTranslationsTestData.twine")
        val availableIntentionActions = getAvailableIntentions("EditTranslationsTestData.java")
        availableIntentionActions[0].invoke(project, editor, file)
        checkResultByFile("EditTranslationsTestData.twine", "AfterEditTranslationsTestData.twine", false)
    }
}