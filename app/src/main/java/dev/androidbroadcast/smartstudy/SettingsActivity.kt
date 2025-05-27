package dev.androidbroadcast.smartstudy

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.androidbroadcast.smartstudy.presentation.settings.SettingsScreen
import dev.androidbroadcast.smartstudy.presentation.theme.SmartStudyTheme

class SettingsActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        setContent {
            SmartStudyTheme {
                SettingsScreen(sharedPreferences = sharedPreferences)
            }
        }
    }
}