package by.overpass.twap.lang

import by.overpass.twap.Twine
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class TwineColorSettingsPage : ColorSettingsPage {

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = descriptors

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = "Twine"

    override fun getIcon(): Icon = Twine.ICON

    override fun getHighlighter(): SyntaxHighlighter = TwineSyntaxHighlighter()

    override fun getDemoText(): String = """
        [[section1]]
            [label1]
                fi = teksti 1
                en = text 1
                ru = текст 1
                comment = text 1 comment
            [label2]
                fi = teksti 2
                en = text 2
                ru = текст 2
                comment = text 2 comment
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null

    companion object {
        private val descriptors = arrayOf(
            AttributesDescriptor("Id", TwineSyntaxHighlighter.id),
            AttributesDescriptor("Comment", TwineSyntaxHighlighter.comment),
            AttributesDescriptor("Text", TwineSyntaxHighlighter.text),
            AttributesDescriptor("Locale", TwineSyntaxHighlighter.locale)
        )
    }
}