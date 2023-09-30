package ru.unilms.domain.model.calendar

data class DayEventsInfo(
    val dayOfMonth: Int,
    val hasEvents: Boolean,
    val hasLessons: Boolean,
    val hasDeadlines: Boolean
)
