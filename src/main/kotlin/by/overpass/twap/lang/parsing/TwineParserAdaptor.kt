package by.overpass.twap.lang.parsing

import by.overpass.twap.parser.TwineParser
import com.intellij.lang.Language
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree

class TwineParserAdaptor(language: Language, parser: Parser) : ANTLRParserAdaptor(language, parser) {

    override fun parse(parser: Parser?, root: IElementType?): ParseTree {
        return if (root is IFileElementType) {
            (parser as TwineParser).twine()
        } else throw UnsupportedOperationException("Can't parse ${root?.javaClass?.name}")
    }
}