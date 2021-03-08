package by.overpass.twap.lang.psi

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.parser.TwineLexer
import by.overpass.twap.parser.TwineParser
import by.overpass.twap.parser.tokenNames
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType


class TwineParserDefinition : ParserDefinition {

    private val psiElementFactory: PsiElementFactory = DefaultPsiElementFactory

    override fun createLexer(project: Project?): Lexer {
        return ANTLRLexerAdaptor(TwineLanguage, TwineLexer(null))
    }

    override fun createParser(project: Project?): PsiParser {
        return TwineParserAdaptor(TwineLanguage, TwineParser(null))
    }

    override fun getFileNodeType(): IFileElementType = Elements.file

    override fun getCommentTokens(): TokenSet = Elements.TokenSets.comment

    override fun getWhitespaceTokens(): TokenSet = Elements.TokenSets.ws

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createElement(node: ASTNode): PsiElement = psiElementFactory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = TwineFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    object Elements {
        private val tokenIElementTypes: List<TokenIElementType>

        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(
                TwineLanguage,
                TwineParser.VOCABULARY.tokenNames,
                TwineParser.ruleNames
            )
            tokenIElementTypes = PSIElementTypeFactory.getTokenIElementTypes(TwineLanguage)
        }

        val file: IFileElementType = IFileElementType(TwineLanguage)

        operator fun get(id: Int): TokenIElementType {
            return tokenIElementTypes[id]
        }

        object TokenSets {
            val ws: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.WS)
            val comment: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.COMMENT)
            val id: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.ID)
        }
    }
}