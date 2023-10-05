package ru.unilms.domain.model.calendar

import ru.unilms.utils.enums.EventType
import java.util.UUID

data class DayEvent(
    val id: UUID,
    val type: EventType,
    val title: String,
    val time: String,
    val auditorium: String? = null,
    val subject: String? = null
)
