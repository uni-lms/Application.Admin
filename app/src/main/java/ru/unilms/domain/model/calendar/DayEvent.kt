package ru.unilms.domain.model.calendar

import java.time.LocalDateTime

data class DayEvent(
    val title: String,
    val time: LocalDateTime
)
