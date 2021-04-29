package by.overpass.twap.lang.parsing

import by.overpass.twap.lang.TwineLanguage
import by.overpass.twap.lang.parsing.psi.DefaultPsiElementFactory
import by.overpass.twap.lang.parsing.psi.PsiElementFactory
import by.overpass.twap.parser.TwineLexer
import by.overpass.twap.parser.TwineParser
import by.overpass.twap.parser.tokenNames
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType

/**
 * Defines how twine files are parsed
 */
@Suppress("INLINE_CLASS_CAN_BE_USED")
class TwineParserDefinition : ParserDefinition {

    private val psiElementFactory: PsiElementFactory = DefaultPsiElementFactory

    override fun createLexer(project: Project?) = TwineLexerAdapter()

    override fun createParser(project: Project?) = TwineParserAdaptor(TwineLanguage, TwineParser(null))

    override fun getFileNodeType(): IFileElementType = file

    override fun getCommentTokens(): TokenSet = TokenSet.EMPTY

    override fun getWhitespaceTokens(): TokenSet = TokenSets.ws

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun createElement(node: ASTNode): PsiElement = psiElementFactory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = TwineFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?) =
        ParserDefinition.SpaceRequirements.MAY

    companion object {

        val tokens: List<TokenIElementType> = PSIElementTypeFactory.getTokenIElementTypes(TwineLanguage)
        val rules: List<RuleIElementType> = PSIElementTypeFactory.getRuleIElementTypes(TwineLanguage)
        val file: IFileElementType = IFileElementType(TwineLanguage)

        object TokenSets {
            val ws: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.WS)
            val comment: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.COMMENT)
            val id: TokenSet = PSIElementTypeFactory.createTokenSet(TwineLanguage, TwineLexer.ID)
        }

        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(
                    TwineLanguage,
                    TwineParser.VOCABULARY.tokenNames,
                    TwineParser.ruleNames
            )
        }
    }
}
