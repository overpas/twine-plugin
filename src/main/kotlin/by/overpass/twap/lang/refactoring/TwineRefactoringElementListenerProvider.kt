package by.overpass.twap.lang.refactoring

import com.intellij.psi.PsiElement
import com.intellij.refactoring.listeners.RefactoringElementListener
import com.intellij.refactoring.listeners.RefactoringElementListenerProvider

/**
 * Listens to refactoring events in the project
 */
class TwineRefactoringElementListenerProvider : RefactoringElementListenerProvider {

    override fun getListener(element: PsiElement): RefactoringElementListener {
        return object : RefactoringElementListener {
            override fun elementMoved(newElement: PsiElement) {
                // do nothing
            }

            override fun elementRenamed(newElement: PsiElement) {
                // do nothing for now
            }
        }
    }
}