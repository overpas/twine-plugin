package by.overpass.twap.lang.psi

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.lang.psi.factory.DefaultPsiElementFactory
import by.overpass.twap.lang.psi.factory.PsiElementFactory
import by.overpass.twap.parser.TwineLexer
import by.overpass.twap.parser.TwineParser
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree


class TwineParserDefinition : ParserDefinition {

    private val psiElementFactory: PsiElementFactory = DefaultPsiElementFactory

    override fun createLexer(project: Project?): Lexer {
        return ANTLRLexerAdaptor(TwineLanguage, TwineLexer(null))
    }

    override fun createParser(project: Project?): PsiParser {
        return object : ANTLRParserAdaptor(TwineLanguage, TwineParser(null)) {
            override fun parse(parser: Parser?, root: IElementType?): ParseTree {
                return if (root is IFileElementType) {
                    (parser as TwineParser).twine()
                } else throw UnsupportedOperationException("Can't parse ${root?.javaClass?.name}")
            }
        }
    }

    override fun getFileNodeType(): IFileElementType = file

    override fun getCommentTokens(): TokenSet = comment

    override fun getWhitespaceTokens(): TokenSet = ws

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createElement(node: ASTNode): PsiElement = psiElementFactory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = TwineFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    companion object {
        var id: TokenIElementType
        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(TwineLanguage, TwineParser.tokenNames, TwineParser.ruleNames)
            val tokenIElementTypes = PSIElementTypeFactory.getTokenIElementTypes(TwineLanguage)
            id = tokenIElementTypes[TwineLexer.ID]
        }
        val file: IFileElementType = IFileElementType(TwineLanguage)
        val ws: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.WS)
        val comment: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.COMMENT)
    }
}