package by.overpass.twap.lang.navigation

import by.overpass.twap.Twine
import by.overpass.twap.lang.TwineFileType
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.testFramework.fixtures.JavaCodeInsightFixtureTestCase
import org.junit.Test

/**
 * Test for [XmlTwineLabelLineMarkerProvider]
 */
class XmlTwineLabelLineMarkerProviderTest : JavaCodeInsightFixtureTestCase() {

    private val testXmlFileContent = """
                <?xml version="1.0" encoding="utf-8"?>
                <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                    xmlns:app="http://schemas.android.com/apk/res-auto"
                    xmlns:tools="http://schemas.android.com/tools"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    tools:context=".MainActivity">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="@string/label1.la<caret>b1"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintLeft_toLeftOf="parent"
                        app:layout_constraintRight_toRightOf="parent"
                        app:layout_constraintTop_toTopOf="parent" />

                </FrameLayout>
            """.trimIndent()

    @Test
    fun testGutterIconIsVisible() = with(myFixture) {
        configureByText(
            TwineFileType,
            """
                [[section]]
                    [label1.lab1]
                        en = en
                        ru = ru
            """.trimIndent()
        )
        configureByText(
            XmlFileType.INSTANCE,
            testXmlFileContent
        )

        val gutterIcons = findGuttersAtCaret()
        assertEquals(1, gutterIcons.size)
        val twineGutterIcon = gutterIcons.first()
        assertEquals(Twine.icon, twineGutterIcon.icon)
        assertEquals(TwineLabelLineMarkerProvider.MESSAGE_NAVIGATE_TO_TWINE_LABEL, twineGutterIcon.tooltipText)
    }
}