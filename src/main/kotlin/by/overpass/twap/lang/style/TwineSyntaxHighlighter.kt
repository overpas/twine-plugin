package by.overpass.twap.lang.style

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.parser.TwineLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.EMPTY_ARRAY
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.TokenIElementType


class TwineSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return ANTLRLexerAdaptor(TwineLanguage, TwineLexer(null))
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
        if (tokenType !is TokenIElementType) {
            EMPTY_ARRAY
        } else when (tokenType.antlrTokenType) {
            TwineLexer.ID -> TextAttributes.idKeys
            TwineLexer.COMMENT -> TextAttributes.commentKeys
            TwineLexer.TEXT -> TextAttributes.textKeys
            TwineLexer.LOCALE -> TextAttributes.localeKeys
            else -> EMPTY_ARRAY
        }

    object TextAttributes {

        val id: TextAttributesKey = createTextAttributesKey(
            "TWINE_ID",
            DefaultLanguageHighlighterColors.INSTANCE_METHOD
        )
        val idKeys = arrayOf(id)
        val comment: TextAttributesKey = createTextAttributesKey(
            "TWINE_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        val commentKeys = arrayOf(comment)
        val text: TextAttributesKey = createTextAttributesKey(
            "TWINE_TEXT",
            DefaultLanguageHighlighterColors.STRING
        )
        val textKeys = arrayOf(text)
        val locale: TextAttributesKey = createTextAttributesKey(
            "TWINE_LOCALE",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val localeKeys = arrayOf(locale)
    }
}