package by.overpass.twap.lang.navigation

import by.overpass.twap.Twine
import by.overpass.twap.lang.TwineFileType
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.testFramework.fixtures.JavaCodeInsightFixtureTestCase
import org.junit.Test

/**
 * Test for [JavaTwineLabelLineMarkerProvider]
 */
class JavaTwineLabelLineMarkerProviderTest : JavaCodeInsightFixtureTestCase() {

    @Test
    fun testGutterIconIsDisplayed() = with(myFixture) {
        configureByText(
            TwineFileType,
            """
                [[section]]
                    [label1]
                        en = en
                        ru = ru
            """.trimIndent()
        )
        configureByText(
            JavaFileType.INSTANCE,
            """
                package com.example;

                public class Main {

                    public static void main(String[] args) {
                        int a = R.string.labe<caret>l1;
                        System.out.println(a);
                    }
                }
            """.trimIndent()
        )

        val gutterIcons = findGuttersAtCaret()
        assertEquals(1, gutterIcons.size)
        val twineGutterIcon = gutterIcons.first()
        assertEquals(Twine.icon, twineGutterIcon.icon)
        assertEquals(TwineLabelLineMarkerProvider.MESSAGE_NAVIGATE_TO_TWINE_LABEL, twineGutterIcon.tooltipText)
    }
}