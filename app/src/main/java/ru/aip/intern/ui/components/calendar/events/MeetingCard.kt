package ru.aip.intern.ui.components.calendar.events

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.aip.intern.domain.calendar.data.MeetingEvent
import ru.aip.intern.navigation.Screen
import java.util.UUID

@Composable
fun MeetingCard(event: MeetingEvent, navigate: (Screen, UUID) -> Unit) {
    ListItem(
        leadingContent = {
            Icon(
                imageVector = Icons.Outlined.Call,
                contentDescription = null
            )
        },
        headlineContent = { Text(text = event.title) },
        modifier = Modifier.clickable {
            navigate(Screen.Event, event.id)
        }
    )
}