package ru.unilms.domain.calendar.model

data class DayEventsInfo(
    val dayOfMonth: Int,
    val regularEvents: Int,
    val lessons: Int,
    val deadlines: Int
)
