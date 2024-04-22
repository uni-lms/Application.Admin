package ru.aip.intern.ui.components.notifications

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.AddComment
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.HourglassBottom
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.aip.intern.domain.notifications.data.Notification
import ru.aip.intern.domain.notifications.data.NotificationType
import ru.aip.intern.navigation.Screen
import ru.aip.intern.util.formatRelative
import java.util.UUID

@Composable
fun NotificationCard(notification: Notification, navigate: (Screen, UUID) -> Unit) {
    ListItem(
        leadingContent = {
            val icon = when (notification.triggerType) {
                NotificationType.Deadline -> Icons.Outlined.HourglassBottom
                NotificationType.Meeting -> Icons.Outlined.Call
                NotificationType.Comment -> Icons.Outlined.AddComment
                NotificationType.Solution -> Icons.AutoMirrored.Outlined.Assignment
            }

            Icon(imageVector = icon, contentDescription = null)
        },
        headlineContent = {
            Text(text = notification.title)
        },
        supportingContent = {
            if (notification.description != null) {
                Text(text = notification.description)
            }
        },
        trailingContent = {
            Text(text = notification.timestamp.formatRelative())
        },
        modifier = Modifier.clickable {
            val screenToGo = when (notification.triggerType) {
                NotificationType.Deadline -> Screen.Assignment
                NotificationType.Meeting -> Screen.Event
                NotificationType.Comment -> Screen.Solution
                NotificationType.Solution -> Screen.Solution
            }

            navigate(screenToGo, notification.triggerId)
        }
    )
}