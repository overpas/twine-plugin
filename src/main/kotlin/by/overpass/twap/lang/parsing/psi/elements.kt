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

open class TwinePsiElement(node: ASTNode) : ANTLRPsiNode(node)

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

    override fun getReferences(): Array<PsiReference> = arrayOf(
        reference,
        *ReferenceProvidersRegistry.getReferencesFromProviders(this)
    ).requireNoNulls()
}

class TwineLabel(node: ASTNode) : TwinePsiElement(node)

fun Project.createTwineIdentifier(id: String): TwineIdentifier = createDummyTwineFileWithId(id)
    .let { PsiTreeUtil.findChildrenOfType(it, TwineIdentifier::class.java) }
    .last()

fun Project.createDummyTwineFileWithId(id: String): TwineFile = createTwineFile(
    "dummy",
    """
    [[dummy_section]]
        [$id]
            en = dummy text
    """.trimIndent()
)

fun Project.createTwineFile(fileName: String, text: String): TwineFile = PsiFileFactory.getInstance(this)
    .createFileFromText("$fileName.twine", TwineFileType, text)
    .let { it as TwineFile }

class TwineLabelTitle(node: ASTNode) : TwinePsiElement(node)

class TwineTranslation(node: ASTNode) : TwinePsiElement(node)

class TwineComment(node: ASTNode) : TwinePsiElement(node)

class TwineSection(node: ASTNode) : TwinePsiElement(node)

class TwineSectionTitle(node: ASTNode) : TwinePsiElement(node)