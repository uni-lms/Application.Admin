package ru.unilms.domain.calendar.view.component.events

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.runtime.Composable
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.calendar.model.DayEvent
import java.util.UUID

@Composable
fun DeadlineEvent(event: DayEvent, onClick: (Screens, UUID) -> Unit) {
    BaseEvent(
        icon = Icons.Outlined.TaskAlt,
        headlineText = event.title,
        supportingText = event.time,
        onClick = { onClick(Screens.Task, event.id) }
    )
}
