package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.calendar.DayEventsInfo
import ru.unilms.domain.model.calendar.MonthEventsInfo
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val sep2023data = MonthEventsInfo(
        listOf(
            DayEventsInfo(1, hasEvents = true, hasLessons = true, hasDeadlines = true),
            DayEventsInfo(4, hasEvents = false, hasLessons = true, hasDeadlines = false),
            DayEventsInfo(8, hasEvents = true, hasLessons = false, hasDeadlines = false),
            DayEventsInfo(10, hasEvents = false, hasLessons = false, hasDeadlines = true)
        )
    )
    private val oct2023data = MonthEventsInfo(
        listOf(
            DayEventsInfo(8, hasEvents = true, hasLessons = true, hasDeadlines = true),
            DayEventsInfo(16, hasEvents = false, hasLessons = true, hasDeadlines = false),
            DayEventsInfo(3, hasEvents = true, hasLessons = false, hasDeadlines = false),
            DayEventsInfo(12, hasEvents = false, hasLessons = false, hasDeadlines = true)
        )
    )

    fun requestDataForMonth(monthYear: YearMonth): MonthEventsInfo {
        return when (monthYear) {
            YearMonth.of(2023, 9) -> sep2023data
            YearMonth.of(2023, 10) -> oct2023data
            else -> MonthEventsInfo(listOf())
        }
    }
}