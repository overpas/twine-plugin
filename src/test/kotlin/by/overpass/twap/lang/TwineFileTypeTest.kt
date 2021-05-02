package by.overpass.twap.lang

import by.overpass.twap.Twine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

/**
 * TwineFileTypeTest
 */
class TwineFileTypeTest {

    @Test
    fun testTwineFileTypeName() {
        assertEquals("Twine", TwineFileType.name)
    }

    @Test
    fun testTwineFileTypeDescription() {
        assertEquals("Twine file", TwineFileType.description)
    }

    @Test
    fun testTwineFileTypeExt() {
        assertEquals(Twine.EXT, TwineFileType.defaultExtension)
    }

    @Test
    fun testTwineFileTypeIcon() {
        assertSame(Twine.icon, TwineFileType.icon)
    }
}