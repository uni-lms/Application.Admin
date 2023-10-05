package ru.unilms.ui.components.calendar.events

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.School
import androidx.compose.runtime.Composable
import ru.unilms.app.UniAppScreen
import ru.unilms.domain.model.calendar.DayEvent
import java.util.UUID

@Composable
fun LessonEvent(event: DayEvent, onClick: (UniAppScreen, UUID) -> Unit) {
    BaseEvent(
        icon = Icons.Outlined.School,
        overlineText = event.subject,
        headlineText = event.title,
        supportingText = event.time,
        trailingText = event.auditorium,
        onClick = { onClick(UniAppScreen.Lesson, event.id) }
    )
}