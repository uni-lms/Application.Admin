package ru.unilms.domain.calendar.view.component.events

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.runtime.Composable
import ru.unilms.domain.calendar.model.DayEvent

@Composable
fun RegularEvent(event: DayEvent) {
    BaseEvent(
        icon = Icons.Outlined.Event,
        headlineText = event.title,
        supportingText = event.time,
    )
}