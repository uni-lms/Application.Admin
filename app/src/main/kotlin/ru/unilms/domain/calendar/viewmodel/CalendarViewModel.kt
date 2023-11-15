package ru.unilms.domain.calendar.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.calendar.model.DayEvent
import ru.unilms.domain.calendar.model.DayEventsInfo
import ru.unilms.domain.calendar.model.MonthEventsInfo
import ru.unilms.domain.calendar.util.enums.EventType
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val oct2023data = MonthEventsInfo(
        listOf(
            DayEventsInfo(1, regularEvents = 1, lessons = 1, deadlines = 1),
            DayEventsInfo(4, regularEvents = 0, lessons = 1, deadlines = 0),
            DayEventsInfo(8, regularEvents = 1, lessons = 0, deadlines = 0),
            DayEventsInfo(10, regularEvents = 0, lessons = 1, deadlines = 1)
        )
    )

    fun requestDataForMonth(monthYear: YearMonth): MonthEventsInfo {
        return when (monthYear) {
            YearMonth.of(2023, 10) -> oct2023data
            else -> MonthEventsInfo(listOf())
        }
    }

    fun requestDataForDay(date: LocalDate): List<DayEvent> {
        val id = UUID.randomUUID()
        val time = "10:15"
        val lessonTime = "8.30 - 10.00"
        return when (date) {
            LocalDate.parse("2023-10-01") -> listOf(
                DayEvent(id, EventType.Lesson, "", lessonTime, "404а-2", "ТП", "Лекция"),
                DayEvent(id, EventType.Deadline, "Дедлайн по Л/Р №1", time),
                DayEvent(id, EventType.Regular, "Кастомное событие", time),
            )

            LocalDate.parse("2023-10-04") -> listOf(
                DayEvent(id, EventType.Lesson, "", lessonTime, "404а-2", "ТП", "Лекция"),
            )

            LocalDate.parse("2023-10-08") -> listOf(
                DayEvent(id, EventType.Regular, "Кастомное событие", time),
            )

            LocalDate.parse("2023-10-10") -> listOf(
                DayEvent(id, EventType.Lesson, "", lessonTime, "404а-2", "ТП", "Лекция"),
                DayEvent(id, EventType.Deadline, "Дедлайн по Л/Р №1", time),
            )

            else -> listOf()
        }
    }
}