package by.overpass.twap.lang.reference.identifier

import by.overpass.twap.lang.findTwineIds
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult

/**
 * Reference to [by.overpass.twap.lang.parsing.psi.TwineIdentifier] implementation
 */
class TwineIdentifierReference(
    element: PsiElement,
    textRange: TextRange
) : PsiPolyVariantReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val id: String by lazy {
        element.text.substring(textRange.startOffset, textRange.endOffset)
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> = myElement.project
        .findTwineIds(id)
        .map { PsiElementResolveResult(it) }
        .toTypedArray()

    override fun handleElementRename(newElementName: String): PsiElement {
        val project = myElement.project
        val dumbService = DumbService.getInstance(project)

        project.findFiles(XmlFileType.INSTANCE)
            .flatMap { file -> PsiTreeUtil.findChildrenOfType(file, XmlAttributeValue::class.java) }
            .filter { it.value == id }
            .map { createRenameProcessor(it, newElementName) }
            .forEach {
                dumbService.smartInvokeLater { it.run() }
                // TODO: Gradle sync after that
            }

        return super.handleElementRename(newElementName)
    }

    private fun createRenameProcessor(myPsiElement: PsiElement, newName: String): RenameProcessor {
        val project = myElement.project
        return RenameProcessor(
            project,
            myPsiElement,
            newName,
            GlobalSearchScope.projectScope(project),
            false,
            false
        )
    }
}