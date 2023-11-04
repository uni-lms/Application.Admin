package ru.unilms.ui.components.calendar.events

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.runtime.Composable
import ru.unilms.domain.model.calendar.DayEvent

@Composable
fun RegularEvent(event: DayEvent) {
    BaseEvent(
        icon = Icons.Outlined.Event,
        headlineText = event.title,
        supportingText = event.time,
    )
}