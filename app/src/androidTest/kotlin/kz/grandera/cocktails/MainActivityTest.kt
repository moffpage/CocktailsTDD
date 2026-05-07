package kz.grandera.cocktails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText

import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shows_cocktails_list_screen() {
        composeRule.onNodeWithText("Search").assertIsDisplayed()
        composeRule.onNodeWithText("Non-alcoholic").assertIsDisplayed()
        composeRule.onNodeWithText("Alcoholic").assertIsDisplayed()
    }
}
