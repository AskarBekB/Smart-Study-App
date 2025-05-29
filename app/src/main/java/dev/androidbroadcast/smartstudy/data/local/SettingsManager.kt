package dev.androidbroadcast.smartstudy.data.local

import android.content.Context
import android.content.SharedPreferences
import dev.androidbroadcast.smartstudy.util.Constants.KEY_DARK_THEME
import dev.androidbroadcast.smartstudy.util.Constants.KEY_FONT_SCALE
import dev.androidbroadcast.smartstudy.util.Constants.KEY_NOTIFICATIONS

class SettingsManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)


    // Notifications
    fun isNotificationsEnabled(): Boolean =
        prefs.getBoolean(KEY_NOTIFICATIONS, true)

    fun setNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply()
    }

    // Dark theme
    fun isDarkThemeEnabled(): Boolean =
        prefs.getBoolean(KEY_DARK_THEME, false)

    fun setDarkThemeEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_THEME, enabled).apply()
    }

    // Font scale
    fun getFontScale(): Float =
        prefs.getFloat(KEY_FONT_SCALE, 1.0f)

    fun setFontScale(scale: Float) {
        prefs.edit().putFloat(KEY_FONT_SCALE, scale).apply()
    }
}
