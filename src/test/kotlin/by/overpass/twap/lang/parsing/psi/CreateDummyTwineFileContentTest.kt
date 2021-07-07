package by.overpass.twap.lang.parsing.psi

import org.junit.Assert.assertEquals
import org.junit.Test

class CreateDummyTwineFileContentTest {

    @Test
    fun `dummy twine file content is generated correctly`() {
        val expected = """
            [[dummy_section]]
                [label]
                    en = text
                    ru = текст
            
        """.trimIndent()
        val actual = createDummyTwineFileContent("label", mapOf("en" to "text", "ru" to "текст"))
        assertEquals(expected, actual)
    }
}