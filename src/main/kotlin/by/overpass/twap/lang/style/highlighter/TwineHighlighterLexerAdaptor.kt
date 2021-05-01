package by.overpass.twap.lang.style.highlighter

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.parser.TwineLexer
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor

/**
 * ANTLR4 adapter for twine lexer
 * (used for highlighting, might be different from [by.overpass.twap.lang.parsing.TwineLexerAdapter])
 */
class TwineHighlighterLexerAdaptor(twineLexer: TwineLexer) : ANTLRLexerAdaptor(TwineLanguage, twineLexer)
