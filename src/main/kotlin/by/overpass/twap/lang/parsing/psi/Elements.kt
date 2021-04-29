/**
 * Twine PsiElements
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
 * Base twine psi
 */
open class TwinePsiElement(node: ASTNode) : ANTLRPsiNode(node)

/**
 * Twine identifier psi
 */
class TwineIdentifier(node: ASTNode) : TwinePsiElement(node), PsiNameIdentifierOwner {

    private val idNode: ASTNode?
        get() = node.findChildByType(tokens[TwineLexer.ID])

    override fun getName(): String? = idNode?.text

    override fun setName(name: String): PsiElement =
        idNode
            ?.let {
                val newNode = project.createTwineIdentifier(name).firstChild.node
                node.replaceChild(it, newNode)
            }
            .let { this }

    override fun getNameIdentifier(): PsiElement? = idNode?.psi

    override fun getReference(): PsiReference? =
        idNode
            ?.let {
                val startOffsetInParent = it.startOffsetInParent
                val endOffset = startOffsetInParent + it.textLength
                val textRange = TextRange(startOffsetInParent, endOffset)
                TwineIdentifierReference(this, textRange)
            }

    override fun getReferences(): Array<PsiReference> = arrayOf(
        reference,
        *ReferenceProvidersRegistry.getReferencesFromProviders(this)
    ).requireNoNulls()
}

/**
 * Twine label psi
 */
class TwineLabel(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine label title psi
 */
class TwineLabelTitle(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine translation psi
 */
class TwineTranslation(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine comment psi
 */
class TwineComment(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine section psi
 */
class TwineSection(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine section title psi
 */
class TwineSectionTitle(node: ASTNode) : TwinePsiElement(node)

/**
 * @param id
 * @return corresponding [TwineIdentifier] in the newly created dummy file
 */
fun Project.createTwineIdentifier(id: String): TwineIdentifier = createDummyTwineFileWithId(id)
    .let { PsiTreeUtil.findChildrenOfType(it, TwineIdentifier::class.java) }
    .last()

/**
 * @param id
 * @return the created dummy [TwineFile] with [id] label
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
 * @return the [TwineFile] with [fileName] and [text] as content
 */
fun Project.createTwineFile(fileName: String, text: String): TwineFile = PsiFileFactory.getInstance(this)
    .createFileFromText("$fileName.twine", TwineFileType, text)
    .let { it as TwineFile }
