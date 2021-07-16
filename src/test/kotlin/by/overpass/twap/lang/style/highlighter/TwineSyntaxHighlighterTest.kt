package by.overpass.twap.lang.style.highlighter

import by.overpass.twap.lang.parsing.TwineParserDefinition.Companion.tokens
import by.overpass.twap.parser.TwineLexer
import by.overpass.twap.parser.TwineParser
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.EMPTY_ARRAY
import com.intellij.psi.tree.IElementType
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class TwineSyntaxHighlighterTest(
    private val expectedKeys: Array<TextAttributesKey>,
    private val iElementType: IElementType,
) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> = listOf(
            arrayOf(TwineSyntaxHighlighter.TextAttributes.idKeys, tokens[TwineLexer.ID]),
            arrayOf(TwineSyntaxHighlighter.TextAttributes.commentKeys, tokens[TwineLexer.COMMENT]),
            arrayOf(TwineSyntaxHighlighter.TextAttributes.textKeys, tokens[TwineLexer.TEXT]),
            arrayOf(TwineSyntaxHighlighter.TextAttributes.localeKeys, tokens[TwineLexer.LOCALE]),
            arrayOf(EMPTY_ARRAY, tokens[TwineLexer.T__2]),
            arrayOf(EMPTY_ARRAY, tokens[TwineParser.RULE_label]),
        )
    }

    private val twineSyntaxHighlighter = TwineSyntaxHighlighter()

    @Test
    fun `token highlight correct`() {
        assertArrayEquals(expectedKeys, twineSyntaxHighlighter.getTokenHighlights(iElementType))
    }
}