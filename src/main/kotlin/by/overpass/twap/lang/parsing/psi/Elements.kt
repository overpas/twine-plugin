/**
 * Twine psi elements
 */

package by.overpass.twap.lang.parsing.psi

import by.overpass.twap.lang.TwineFileType
import by.overpass.twap.lang.parsing.TwineFile
import by.overpass.twap.lang.parsing.TwineParserDefinition.Companion.rules
import by.overpass.twap.lang.parsing.TwineParserDefinition.Companion.tokens
import by.overpass.twap.lang.reference.identifier.TwineIdentifierReference
import by.overpass.twap.parser.TwineLexer
import by.overpass.twap.parser.TwineParser
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
        val array: Array<PsiReference?> = arrayOfNulls(fromProviders.size + 1)
        System.arraycopy(fromProviders, 0, array, 1, fromProviders.size)
        array[0] = reference
        return array.requireNoNulls()
    }
}

/**
 * Twine label psi element
 */
class TwineLabel(node: ASTNode) : TwinePsiElement(node) {
    /**
     * [TwineTranslation]s associated with this [TwineLabel]
     */
    val translations: List<TwineTranslation>
        get() = findChildrenByType(rules[TwineParser.RULE_translation])

    /**
     * @param translations map of locales to texts
     */
    fun updateTranslations(translations: Map<String, String>) {
        PsiTreeUtil.findChildOfType(this, TwineIdentifier::class.java)
            ?.text
            ?.let { id ->
                val dummyFile = project.createDummyTwineFileWithLabelAndTranslations(id, translations)
                PsiTreeUtil.findChildOfType(dummyFile, TwineLabel::class.java)
                    ?.node
                    ?.let { newLabelNode ->
                        node.treeParent.replaceChild(node, newLabelNode)
                    }
            }
    }
}

/**
 * Twine label title psi element
 */
class TwineLabelTitle(node: ASTNode) : TwinePsiElement(node)

/**
 * Twine translation psi element
 */
class TwineTranslation(node: ASTNode) : TwinePsiElement(node) {

    private val locale: PsiElement?
        get() = findChildByType(tokens[TwineLexer.LOCALE])

    /**
     * Text value of this translation's locale
     */
    val localeValue: String
        get() = locale?.text ?: ""

    private val text: PsiElement?
        get() = findChildByType(tokens[TwineLexer.TEXT])

    /**
     * Text value of this translation's text without prefix
     */
    val textValue: String
        get() = text?.text
            ?.removePrefix("=")
            ?.trim()
            ?: ""
}

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
 * @param label twine label
 * @param translations locale to text map
 * @return dummy [TwineFile] containing a dummy label with [translations]
 */
fun Project.createDummyTwineFileWithLabelAndTranslations(
    label: String,
    translations: Map<String, String>
): TwineFile = createTwineFile(
    "dummy",
    createDummyTwineFileContent(label, translations)
)

/**
 * @param id
 * @return [TwineIdentifier] from dummy file with [id]
 */
fun Project.createTwineIdentifier(id: String): TwineIdentifier = createDummyTwineFileWithId(id)
    .let { PsiTreeUtil.findChildrenOfType(it, TwineIdentifier::class.java) }
    .last()

/**
 * @param fileName
 * @param text
 * @return [TwineFile] with [fileName] and [text] as content
 */
fun Project.createTwineFile(fileName: String, text: String): TwineFile = PsiFileFactory.getInstance(this)
    .createFileFromText("$fileName.twine", TwineFileType, text)
    .let { it as TwineFile }

/**
 * @param label the new label
 * @param translations the new translations (locale to text map)
 * @return string content for a dummy twine file
 */
fun createDummyTwineFileContent(label: String, translations: Map<String, String>): String =
    """
    [[dummy_section]]
        [$label]
${
        translations.map { (locale, translation) -> "            $locale = $translation\n" }
            .toList()
            .fold("") { acc, translation -> "$acc$translation" }
    }
    """.trimIndent()