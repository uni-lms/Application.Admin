package ru.unilms.domain.calendar.model

import ru.unilms.domain.calendar.util.enums.EventType
import java.util.UUID

data class DayEvent(
    val id: UUID,
    val type: EventType,
    val title: String,
    val time: String,
    val auditorium: String? = null,
    val subject: String? = null,
    val lessonType: String? = null
)
