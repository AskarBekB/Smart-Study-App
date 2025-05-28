package dev.androidbroadcast.smartstudy.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    onSettingsClick: () -> Unit,
    onBooksClick: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(12.dp))
        NavigationDrawerItem(
            label = { Text("Settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            selected = false,
            onClick = onSettingsClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        NavigationDrawerItem(
            label = { Text("Books") },
            icon = { Icon(Icons.Default.ThumbUp, contentDescription = null) },
            selected = false,
            onClick = onBooksClick
        )
    }
}
