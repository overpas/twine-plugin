package by.overpass.twap.lang.parsing

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.parser.TwineLexer
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor

class TwineLexerAdapter(lexer: TwineLexer = TwineLexer(null)) : ANTLRLexerAdaptor(TwineLanguage, lexer)