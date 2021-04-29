package by.overpass.twap.lang.style.annotator

import by.overpass.twap.lang.parsing.psi.TwineIdentifier
import by.overpass.twap.lang.style.highlighter.TwineSyntaxHighlighter
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.runReadAction
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.Trees

/**
 * Annotates duplicate labels in Twine files
 */
class TwineAnnotator : ExternalAnnotator<PsiFile, List<TwineAnnotator.Issue>>() {

    override fun collectInformation(file: PsiFile): PsiFile = file

    override fun doAnnotate(file: PsiFile): List<Issue> = runReadAction {
        val twineIdentifiers = PsiTreeUtil.collectElements(file) { it is TwineIdentifier }.toList()
        val uniqueTwineIdentifiers = Trees.toMap(twineIdentifiers)
        val issues: MutableList<Issue> = mutableListOf()
        for (twineIdentifier in twineIdentifiers) {
            if (!uniqueTwineIdentifiers.containsValue(twineIdentifier)) {
                val msg = "Duplicate labels: ${twineIdentifier.text}"
                issues += Issue(msg, twineIdentifier)
            }
        }
        issues
    }

    override fun apply(
        file: PsiFile,
        issues: List<Issue>,
        holder: AnnotationHolder
    ) {
        for ((msg, offendingNode) in issues) {
            val range = offendingNode.textRange
            holder
                .newAnnotation(HighlightSeverity.ERROR, msg)
                .range(range)
                .textAttributes(TwineSyntaxHighlighter.TextAttributes.id)
                .highlightType(ProblemHighlightType.ERROR)
                .create()
        }
    }

    /**
     * @property msg
     * @property offendingNode
     */
    data class Issue(val msg: String, val offendingNode: PsiElement)
}
