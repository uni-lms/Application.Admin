package ru.aip.intern.ui.state

import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import ru.aip.intern.domain.calendar.data.DayEvents
import ru.aip.intern.domain.calendar.data.MonthEvents
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val isRefreshing: Boolean = false,
    val isDayRefreshing: Boolean = false,
    val firstDayOfWeek: DayOfWeek = firstDayOfWeekFromLocale(),
    val daysOfWeek: List<DayOfWeek> = daysOfWeek(),
    val currentMonth: YearMonth = YearMonth.now(),
    val startMonth: YearMonth = currentMonth.minusMonths(3),
    val endMonth: YearMonth = currentMonth.plusMonths(3),
    val today: LocalDate = LocalDate.now(),
    val events: MonthEvents = MonthEvents(
        year = today.year,
        month = today.monthValue,
        days = emptyList()
    ),
    val dayEvents: DayEvents = DayEvents(
        year = today.year,
        month = today.monthValue,
        day = today.dayOfMonth,
        events = emptyList()
    ),

    )
