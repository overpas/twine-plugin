package by.overpass.twap.lang.parsing.psi

import com.intellij.lang.ASTNode
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

open class TwinePsiElement(node: ASTNode) : ANTLRPsiNode(node)

class TwineLabel(node: ASTNode) : TwinePsiElement(node)

class TwineLabelTitle(node: ASTNode) : TwinePsiElement(node)

class TwineTranslation(node: ASTNode) : TwinePsiElement(node)

class TwineComment(node: ASTNode) : TwinePsiElement(node)

class TwineSection(node: ASTNode) : TwinePsiElement(node)

class TwineSectionTitle(node: ASTNode) : TwinePsiElement(node)
