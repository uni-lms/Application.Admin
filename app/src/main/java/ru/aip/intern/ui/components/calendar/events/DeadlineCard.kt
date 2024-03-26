package ru.aip.intern.ui.components.calendar.events

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.aip.intern.domain.calendar.data.DeadlineEvent

@Composable
fun DeadlineCard(event: DeadlineEvent) {
    ListItem(headlineContent = { Text(text = event.title) })
}