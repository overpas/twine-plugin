/**
 * Twine psi elements
 */

package by.overpass.twap.lang.parsing.psi

import by.overpass.twap.lang.TwineFileType
import by.overpass.twap.lang.parsing.TwineFile
import by.overpass.twap.lang.parsing.TwineParserDefinition.Companion.tokens
import by.overpass.twap.lang.reference.identifier.TwineIdentifierReference
import by.overpass.twap.parser.TwineLexer
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

/**
 * Base twine psi element
 */
open class TwinePsiElement(node: ASTNode) : ANTLRPsiNode(node)

/**
 * Twine identifier psi element
 */
class TwineIdentifier(node: ASTNode) : TwinePsiElement(node), PsiNameIdentifierOwner {

    private val idNode: ASTNode?
        get() = node.findChildByType(tokens[TwineLexer.ID])

    override fun getName(): String? =
        idNode?.text

    override fun setName(name: String): PsiElement = idNode
        ?.let {
            val newNode = project.createTwineIdentifier(name).firstChild.node
            node.replaceChild(it, newNode)
        }
        .let { this }

    override fun getNameIdentifier(): PsiElement? = idNode?.psi

    override fun getReference(): PsiReference? = idNode
        ?.let {
            val startOffsetInParent = it.startOffsetInParent
            val endOffset = startOffsetInParent + it.textLength
            val textRange = TextRange(startOffsetInParent, endOffset)
            TwineIdentifierReference(this, textRange)
        }

    override fun getReferences(): Array<PsiReference> {
        val fromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(this)
        val array = arrayOfNulls<PsiReference>(fromProviders.size + 1)
        System.arraycopy(fromProviders, 0, array, 1, fromProviders.size)
        array[0] = reference
        return array.requireNoNulls()
    }
}

/**
 * Twine label psi element
 */
class TwineLabel(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine label title psi element
 */
class TwineLabelTitle(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine translation psi element
 */
class TwineTranslation(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine comment psi element
 */
class TwineComment(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine section psi element
 */
class TwineSection(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine section title psi element
 */
class TwineSectionTitle(node: ASTNode) : TwinePsiElement(node)

/**
 * @param id
 * @return [TwineIdentifier] from dummy file with [id]
 */
fun Project.createTwineIdentifier(id: String): TwineIdentifier = createDummyTwineFileWithId(id)
    .let { PsiTreeUtil.findChildrenOfType(it, TwineIdentifier::class.java) }
    .last()

/**
 * @param id
 * @return dummy [TwineFile] containing a label with [id]
 */
fun Project.createDummyTwineFileWithId(id: String): TwineFile = createTwineFile(
    "dummy",
    """
    [[dummy_section]]
        [$id]
            en = dummy text
    """.trimIndent()
)

/**
 * @param fileName
 * @param text
 * @return [TwineFile] with [fileName] and [text] as content
 */
fun Project.createTwineFile(fileName: String, text: String): TwineFile = PsiFileFactory.getInstance(this)
    .createFileFromText("$fileName.twine", TwineFileType, text)
    .let { it as TwineFile }