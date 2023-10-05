package ru.unilms.ui.components.calendar.events

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Task
import androidx.compose.runtime.Composable
import ru.unilms.app.UniAppScreen
import ru.unilms.domain.model.calendar.DayEvent
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun DeadlineEvent(event: DayEvent, onClick: (UniAppScreen, UUID) -> Unit) {
    BaseEvent(
        icon = Icons.Outlined.Task,
        headlineText = event.title,
        supportingText = event.time.format(DateTimeFormatter.ofPattern("HH:mm")),
        onClick = { onClick(UniAppScreen.Task, event.id) }
    )
}
