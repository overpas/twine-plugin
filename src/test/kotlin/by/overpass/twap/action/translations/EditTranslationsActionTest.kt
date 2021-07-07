package by.overpass.twap.action.translations

import by.overpass.twap.ServiceLocator
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

class EditTranslationsActionTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "src/test/testData"

    fun testEditTranslationsIntentionActionIsAvailable() = with(myFixture) {
        configureByFiles("EditTranslationsTestData.java", "EditTranslationsTestData.twine")
        val availableIntentionActions = getAvailableIntentions("EditTranslationsTestData.java")
        assertEquals(EditTranslationsAction.NAME, availableIntentionActions[0].familyName)
    }

    fun testTranslationsEdited() = with(myFixture) {
        ServiceLocator.dialogFactoryField = FakeDialogFactory()
        ServiceLocator.gradleSyncServiceField = FakeGradleSyncService()
        configureByFiles("EditTranslationsTestData.java", "EditTranslationsTestData.twine")
        val availableIntentionActions = getAvailableIntentions("EditTranslationsTestData.java")
        availableIntentionActions[0].invoke(project, editor, file)
        checkResultByFile("EditTranslationsTestData.twine", "EditTranslationsTestDataAfter.twine", false)
    }
}