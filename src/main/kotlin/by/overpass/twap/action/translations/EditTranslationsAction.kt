package by.overpass.twap.action.translations

import by.overpass.twap.ServiceLocator
import by.overpass.twap.action.DialogFactory
import by.overpass.twap.action.PsiElementFinderIntentionAction
import by.overpass.twap.lang.findTwineIds
import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import by.overpass.twap.lang.parsing.psi.TwineLabel
import by.overpass.twap.service.GradleSyncService
import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.castSafelyTo

/**
 * Action that enables translation editing
 */
class EditTranslationsAction(
    private val gradleSyncService: GradleSyncService = ServiceLocator.gradleSyncService,
    private val dialogFactory: DialogFactory = ServiceLocator.dialogFactory
) : PsiElementFinderIntentionAction(), HighPriorityAction {

    override fun getText(): String = NAME

    override fun isAvailable(
        project: Project,
        editor: Editor?,
        file: PsiFile?
    ): Boolean {
        if (editor != null && file != null) {
            val psiReferenceExpression = getPsiReferenceExpression(editor, file)
            return psiReferenceExpression?.text?.startsWith("R.string.") ?: false
        }
        return false
    }

    override fun invoke(
        project: Project,
        editor: Editor?,
        file: PsiFile?
    ) {
        if (editor != null && file != null) {
            getTwineIdentifierOrNull(project, editor, file)
                ?.let { twineIdentifier ->
                    val translationsModel = TranslationsModel()
                    val twineLabel = twineIdentifier.parent.parent as TwineLabel
                    twineLabel.translations.forEach { translation ->
                        translationsModel.setTranslation(translation.localeValue, translation.textValue)
                    }
                    val dialog = dialogFactory.createEditTranslationsDialog(
                        project,
                        twineLabel,
                        translationsModel,
                        text
                    )
                    if (!dialog.showAndGet()) {
                        return
                    }
                    runWriteCommandAction(project) {
                        twineLabel.updateTranslations(translationsModel.data)
                        gradleSyncService.syncProject(project)
                    }
                }
        }
    }

    private fun getPsiReferenceExpression(editor: Editor, file: PsiFile): PsiReferenceExpression? =
        when (val psiElement = getPsiElement(file, editor)) {
            null -> null
            is PsiReferenceExpression -> psiElement
            else -> PsiTreeUtil.getParentOfType(psiElement, PsiReferenceExpression::class.java)
        }

    private fun getTwineIdentifierOrNull(
        project: Project,
        editor: Editor,
        file: PsiFile
    ): TwineIdentifier? =
        getPsiReferenceExpression(editor, file)
            ?.lastChild
            ?.castSafelyTo<PsiIdentifier>()
            ?.let { project.findTwineIds(it.text) }
            ?.firstOrNull()

    companion object {
        const val NAME = "Edit translations"
    }
}