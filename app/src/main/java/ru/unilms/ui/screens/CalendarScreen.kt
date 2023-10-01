package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import ru.unilms.data.AppBarState
import ru.unilms.ui.components.calendar.Day
import ru.unilms.ui.components.calendar.DaysOfWeek
import ru.unilms.ui.components.calendar.MonthName
import ru.unilms.viewmodels.CalendarViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(onComposing: (AppBarState) -> Unit) {

    val viewModel = hiltViewModel<CalendarViewModel>()
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val daysOfWeek = daysOfWeek()
    var currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val state = rememberCalendarState(
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
        startMonth = startMonth,
        endMonth = endMonth,
    )
    val scope = rememberCoroutineScope()
    var data by remember { mutableStateOf(viewModel.requestDataForMonth(currentMonth)) }
    var isModalOpened by remember { mutableStateOf(false) }
    var selectedDay: CalendarDay? by remember {
        mutableStateOf(CalendarDay(LocalDate.now(), DayPosition.MonthDate))
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = {
                    IconButton(
                        onClick = {
                            val month = currentMonth.minusMonths(1)
                            currentMonth = month
                            data = viewModel.requestDataForMonth(currentMonth)
                            scope.launch {
                                state.animateScrollToMonth(month)
                            }
                        }
                    ) {
                        Icon(Icons.Outlined.ChevronLeft, null)
                    }
                    IconButton(
                        onClick = {
                            val month = currentMonth.plusMonths(1)
                            currentMonth = month
                            data = viewModel.requestDataForMonth(currentMonth)
                            scope.launch {
                                state.animateScrollToMonth(month)
                            }
                        }) {
                        Icon(Icons.Outlined.ChevronRight, null)
                    }
                }
            )
        )
    }


    Column(modifier = Modifier.padding(10.dp)) {
        HorizontalCalendar(
            dayContent = {
                Day(
                    day = it,
                    eventsInfo = data.dayEventsInfo.firstOrNull { item -> item.dayOfMonth == it.date.dayOfMonth }
                ) {
                    selectedDay = it
                    isModalOpened = true
                }
            },
            state = state,
            monthHeader = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {
                    MonthName(month = it)
                    DaysOfWeek(daysOfWeek = daysOfWeek)
                }
            }
        )
    }

    when (isModalOpened) {
        true -> AlertDialog(
            onDismissRequest = {
                isModalOpened = false
                selectedDay = null
            }, confirmButton = {
                TextButton(onClick = {
                    isModalOpened = false
                    selectedDay = null
                }) {
                    Text(text = "Закрыть")
                }
            }, title = {
                Text(
                    selectedDay?.date?.format(DateTimeFormatter.ofPattern("d MMMM, EEE")) ?: ""
                )
            })

        false -> null
    }

}