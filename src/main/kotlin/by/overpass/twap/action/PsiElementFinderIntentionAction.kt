package by.overpass.twap.action

import com.intellij.codeInsight.intention.AbstractIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

/**
 * Intention action that can find a PsiElement by caret position
 */
abstract class PsiElementFinderIntentionAction : AbstractIntentionAction() {
    /**
     * @param file the file where the element is searched
     * @param editor instance of [Editor]
     * @return the [PsiElement] at caret position or null
     */
    protected open fun getPsiElement(file: PsiFile, editor: Editor): PsiElement? {
        val offset = editor.caretModel.offset
        val element = file.findElementAt(offset)
        return element?.parent
    }
}