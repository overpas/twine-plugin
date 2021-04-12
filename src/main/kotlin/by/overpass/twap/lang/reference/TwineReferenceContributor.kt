package by.overpass.twap.lang.reference

import by.overpass.twap.lang.reference.identifier.TwineIdentifierStringLiteralReferenceProvider
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class TwineReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            psiElement(PsiLiteralExpression::class.java),
            TwineIdentifierStringLiteralReferenceProvider()
        )
    }
}