package by.overpass.twap

import by.overpass.twap.parser.TwineLexer
import by.overpass.twap.parser.TwineParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    val lexer = TwineLexer(
        CharStreams.fromString(
            """
[[section1.jhjh.l.ll]]
    [label1]
        en = dfdf
        ru = fbfb
        fi = ddfbb
[[section2.jhjh.l.ll]]
    [label1]
        en = dfdf
        ru = fbfb
        fi = ddfbb\njbjb
    """
        )
    )
    val parser = TwineParser(CommonTokenStream(lexer))
    parser.twine()
        .section(1)
        .label(0)
        .translation(1)
        .TEXT()
        .let {
            println(it.text)
        }
}