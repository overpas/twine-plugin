package by.overpass.twap.lang.refactoring.rename

import by.overpass.twap.Twine
import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

class TwineNamesValidator : NamesValidator {

    override fun isKeyword(name: String, project: Project?): Boolean = false

    override fun isIdentifier(name: String, project: Project?): Boolean =
        name.matches(Twine.twineIdentifierRegex)
}