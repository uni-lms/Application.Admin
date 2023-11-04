package ru.unilms.ui.components.courses.items

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BaseItem(icon: ImageVector, name: String, onClick: () -> Unit = {}) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        leadingContent = {
            Icon(imageVector = icon, contentDescription = null)
        },
        headlineContent = {
            Text(text = name)
        }
    )
}