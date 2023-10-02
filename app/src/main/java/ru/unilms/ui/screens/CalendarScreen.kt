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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ru.unilms.data.AppBarState
import ru.unilms.ui.components.calendar.Day
import ru.unilms.ui.components.calendar.DaysOfWeek
import ru.unilms.ui.components.calendar.MonthName
import ru.unilms.viewmodels.CalendarViewModel
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(onComposing: (AppBarState) -> Unit) {

    val viewModel = hiltViewModel<CalendarViewModel>()
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val daysOfWeek = daysOfWeek()
    val currentMonth = remember { YearMonth.now() }
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
        mutableStateOf(null)
    }
    val visibleMonth = rememberFirstCompletelyVisibleMonth(state)
    LaunchedEffect(visibleMonth) {
        // Clear selection if we scroll to a new month.
        selectedDay = null
        isModalOpened = false
        data = viewModel.requestDataForMonth(visibleMonth.yearMonth)
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                            }
                        }
                    ) {
                        Icon(Icons.Outlined.ChevronLeft, null)
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
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

@Composable
fun rememberFirstCompletelyVisibleMonth(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    // Only take non-null values as null will be produced when the
    // list is mid-scroll as no index will be completely visible.
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.completelyVisibleMonths.firstOrNull() }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private val CalendarLayoutInfo.completelyVisibleMonths: List<CalendarMonth>
    get() {
        val visibleItemsInfo = this.visibleMonthsInfo.toMutableList()
        return if (visibleItemsInfo.isEmpty()) {
            emptyList()
        } else {
            val lastItem = visibleItemsInfo.last()
            val viewportSize = this.viewportEndOffset + this.viewportStartOffset
            if (lastItem.offset + lastItem.size > viewportSize) {
                visibleItemsInfo.removeLast()
            }
            val firstItem = visibleItemsInfo.firstOrNull()
            if (firstItem != null && firstItem.offset < this.viewportStartOffset) {
                visibleItemsInfo.removeFirst()
            }
            visibleItemsInfo.map { it.month }
        }
    }