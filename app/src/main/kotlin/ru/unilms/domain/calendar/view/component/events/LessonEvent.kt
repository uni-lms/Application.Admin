package ru.unilms.domain.calendar.view.component.events

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.School
import androidx.compose.runtime.Composable
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.calendar.model.DayEvent
import java.util.UUID

@Composable
fun LessonEvent(event: DayEvent, onClick: (Screens, UUID) -> Unit) {
    BaseEvent(
        icon = Icons.Outlined.School,
        overlineText = event.lessonType,
        headlineText = event.subject ?: "",
        supportingText = event.time,
        trailingText = event.auditorium,
        onClick = { onClick(Screens.Lesson, event.id) }
    )
}