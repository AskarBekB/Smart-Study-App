package dev.androidbroadcast.smartstudy

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SettingsActivity>()

    @Test
    fun settingsScreen_switchesWorkCorrectly() {
        // Header
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()

        // Switcher
        composeTestRule.onNodeWithText("Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dark Theme").assertIsDisplayed()

        // Finds all switches by clickAction
        val switches = composeTestRule.onAllNodes(hasClickAction())
        switches.assertCountEquals(2) // Check, that both of them exists

        // Toggle them
        switches[0].performClick() // Notifications
        switches[1].performClick() // Dark Theme

        // Testing on text sizes
        composeTestRule.onNode(hasText("Text size", substring = true)).assertIsDisplayed()
    }
}

