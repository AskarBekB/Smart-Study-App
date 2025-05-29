package dev.androidbroadcast.smartstudy.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    notificationsEnabled: Boolean,
    darkThemeEnabled: Boolean,
    fontScale: Float,
    onNotificationsChange: (Boolean) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    onFontScaleChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        SettingRow(
            label = "Notifications",
            control = {
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = onNotificationsChange
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        SettingRow(
            label = "Dark Theme",
            control = {
                Switch(
                    checked = darkThemeEnabled,
                    onCheckedChange = onDarkThemeChange
                )
            }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Text size: ${"%.1f".format(fontScale)}×",
            style = MaterialTheme.typography.bodyMedium
        )
        Slider(
            value = fontScale,
            onValueChange = onFontScaleChange,
            valueRange = 0.8f..1.5f,
            steps = 6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SettingRow(
    label: String,
    control: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        control()
    }
}
