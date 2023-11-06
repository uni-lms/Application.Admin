package ru.unilms.domain.calendar.view.component.events

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BaseEvent(
    icon: ImageVector,
    headlineText: String,
    supportingText: String,
    overlineText: String? = null,
    trailingText: String? = null,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        leadingContent = { Icon(imageVector = icon, contentDescription = null) },
        headlineContent = { Text(text = headlineText) },
        supportingContent = { Text(text = supportingText) },
        overlineContent = {
            if (overlineText != null) {
                Text(text = overlineText)
            }
        },
        trailingContent = {
            if (trailingText != null) {
                Text(text = trailingText)
            }
        }
    )
}