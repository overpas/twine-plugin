package by.overpass.twap.lang.style.highlighter

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.parser.TwineLexer
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor

/**
 * Lexer adapter for highlighter
 */
class TwineHighlighterLexerAdaptor(twineLexer: TwineLexer) : ANTLRLexerAdaptor(TwineLanguage, twineLexer)
