package dev.androidbroadcast.smartstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import dev.androidbroadcast.smartstudy.data.local.SettingsManager
import dev.androidbroadcast.smartstudy.presentation.theme.SmartStudyTheme
import dev.androidbroadcast.smartstudy.presentation.settings.SettingsScreen

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(this)

        setContent {
            var notificationsEnabled by remember { mutableStateOf(settingsManager.isNotificationsEnabled()) }
            var isDarkTheme         by remember { mutableStateOf(settingsManager.isDarkThemeEnabled()) }
            var fontScale           by remember { mutableStateOf(settingsManager.getFontScale()) }

            SmartStudyTheme(
                darkTheme = isDarkTheme,
                fontScale = fontScale
            ) {
                SettingsScreen(
                    notificationsEnabled = notificationsEnabled,
                    darkThemeEnabled     = isDarkTheme,
                    fontScale            = fontScale,
                    onNotificationsChange = { newValue ->
                        notificationsEnabled = newValue
                        settingsManager.setNotificationsEnabled(newValue)
                    },
                    onDarkThemeChange = { newValue ->
                        isDarkTheme = newValue
                        settingsManager.setDarkThemeEnabled(newValue)
                    },
                    onFontScaleChange = { newScale ->
                        fontScale = newScale
                        settingsManager.setFontScale(newScale)
                    }
                )
            }
        }
    }
}
