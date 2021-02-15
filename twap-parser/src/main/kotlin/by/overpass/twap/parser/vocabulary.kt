package by.overpass.twap.parser

import org.antlr.v4.runtime.Vocabulary

val Vocabulary.tokenNames: Array<String>
    get() {
        val tokenList = mutableListOf<String>()
        for (i in 0 .. maxTokenType) {
            tokenList += getLiteralName(i) ?: getSymbolicName(i) ?: "<INVALID>"
        }
        return tokenList.toTypedArray()
    }