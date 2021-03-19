package by.overpass.twap.lang.style.annotator

import by.overpass.twap.lang.style.highlighter.TwineSyntaxHighlighter
import by.overpass.twap.parser.TwineParser
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.runReadAction
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.antlr.intellij.adaptor.psi.Trees


class TwineAnnotator : ExternalAnnotator<PsiFile, List<TwineAnnotator.Issue>>() {

    override fun collectInformation(file: PsiFile): PsiFile = file

    override fun doAnnotate(file: PsiFile): List<Issue> = runReadAction {
        val labelNameNodes = Trees.findAllRuleNodes(file, TwineParser.RULE_label_title)
        val labelNames = Trees.toMap(labelNameNodes)
        val issues = mutableListOf<Issue>()
        for (labelNameNode in labelNameNodes) {
            if (!labelNames.containsValue(labelNameNode)) {
                val text = labelNameNode.text.replace("[\\[\\]]".toRegex(), "")
                val msg = "Duplicate labels: $text"
                issues += Issue(msg, labelNameNode)
            }
        }
        issues
    }

    override fun apply(file: PsiFile, issues: List<Issue>, holder: AnnotationHolder) {
        for ((msg, offendingNode) in issues) {
            val range = offendingNode.textRange
            holder.newAnnotation(HighlightSeverity.ERROR, msg)
                .range(range)
                .textAttributes(TwineSyntaxHighlighter.TextAttributes.id)
                .highlightType(ProblemHighlightType.ERROR)
                .create()
        }
    }

    data class Issue(val msg: String, val offendingNode: PsiElement)
}