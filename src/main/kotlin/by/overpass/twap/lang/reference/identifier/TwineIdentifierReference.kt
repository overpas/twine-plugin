package by.overpass.twap.lang.reference.identifier

import by.overpass.twap.lang.findFiles
import by.overpass.twap.lang.findTwineIds
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttributeValue

/**
 * Reference to [by.overpass.twap.lang.parsing.psi.TwineIdentifier] implementation
 */
class TwineIdentifierReference(
    element: PsiElement,
    textRange: TextRange,
    private val renameProcessorFactory: RenameProcessorFactory = RenameProcessorFactory()
) : PsiPolyVariantReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val id: String by lazy(LazyThreadSafetyMode.NONE) {
        element.text.substring(textRange.startOffset, textRange.endOffset)
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> = myElement.project
        .findTwineIds(id)
        .map { PsiElementResolveResult(it) }
        .toTypedArray()

    override fun handleElementRename(newElementName: String): PsiElement {
        val project = myElement.project

        project.findFiles(XmlFileType.INSTANCE)
            .filter { it.name.endsWith("strings.xml") }
            .flatMap { file -> PsiTreeUtil.findChildrenOfType(file, XmlAttributeValue::class.java) }
            .filter { it.value == id }
            .map { renameProcessorFactory.create(project, it, newElementName) }
            .forEach { it.run() }

        return super.handleElementRename(newElementName)
    }
}