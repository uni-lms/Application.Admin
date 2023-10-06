package ru.unilms.domain.model.calendar

data class DayEventsInfo(
    val dayOfMonth: Int,
    val regularEvents: Int,
    val lessons: Int,
    val deadlines: Int
)
