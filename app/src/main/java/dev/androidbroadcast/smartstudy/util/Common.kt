package dev.androidbroadcast.smartstudy.util

import androidx.compose.ui.graphics.Color
import dev.androidbroadcast.smartstudy.presentation.theme.Green
import dev.androidbroadcast.smartstudy.presentation.theme.Orange
import dev.androidbroadcast.smartstudy.presentation.theme.Red

enum class Priority(
    val title: String,
    val color: Color,
    val value: Int
) {
    LOW(title = "Low", color = Green, value = 0),
    MEDIUM(title = "Medium", color = Orange, value = 1),
    HARD(title = "Hard", color = Red, value = 2);

    companion object {
        fun fromtInt(value: Int) = values().firstOrNull() { it.value == value } ?: MEDIUM
    }
}