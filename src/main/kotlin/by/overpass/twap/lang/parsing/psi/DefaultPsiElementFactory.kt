package by.overpass.twap.lang.parsing.psi

import by.overpass.twap.lang.parsing.TwineParserDefinition.Companion.rules
import by.overpass.twap.parser.TwineParser
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

object DefaultPsiElementFactory : PsiElementFactory {

    override fun createElement(node: ASTNode): PsiElement = when (node.elementType) {
        rules[TwineParser.RULE_label] -> TwineLabel(node)
        rules[TwineParser.RULE_label_title] -> TwineLabelTitle(node)
        rules[TwineParser.RULE_translation] -> TwineTranslation(node)
        rules[TwineParser.RULE_comment] -> TwineComment(node)
        rules[TwineParser.RULE_section] -> TwineSection(node)
        rules[TwineParser.RULE_section_title] -> TwineSectionTitle(node)
        else -> TwinePsiElement(node)
    }
}