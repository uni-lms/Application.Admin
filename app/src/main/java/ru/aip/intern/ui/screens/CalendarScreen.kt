package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.flow.filterNotNull
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.calendar.Day
import ru.aip.intern.ui.components.calendar.DaysOfWeek
import ru.aip.intern.viewmodels.CalendarViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarScreen(title: MutableState<String>) {

    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val daysOfWeek = daysOfWeek()
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(6) }
    val endMonth = remember { currentMonth.plusMonths(6) }
    val calendarState = rememberCalendarState(
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
        startMonth = startMonth,
        endMonth = endMonth,
        outDateStyle = OutDateStyle.EndOfGrid
    )

    val visibleMonth = rememberFirstCompletelyVisibleMonth(calendarState)

    val viewModel: CalendarViewModel = hiltViewModel<CalendarViewModel, CalendarViewModel.Factory>(
        creationCallback = { factory -> factory.create(visibleMonth.yearMonth) }
    )

    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val data = viewModel.data.observeAsState(viewModel.defaultContent)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = {
            title.value = buildTitle(visibleMonth.yearMonth)
            viewModel.refresh()
        }
    )

    title.value = buildTitle(visibleMonth.yearMonth)


    LaunchedEffect(visibleMonth) {
        title.value = buildTitle(visibleMonth.yearMonth)
        viewModel.yearMonth = visibleMonth.yearMonth
        viewModel.refresh()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            HorizontalCalendar(
                dayContent = {
                    Day(
                        day = it,
                        dayEventsOverview = data.value.days.firstOrNull { day -> day.dayOfMonth == it.date.dayOfMonth }) {
                        // TODO
                    }
                },
                state = calendarState,
                monthHeader = {
                    DaysOfWeek(daysOfWeek = daysOfWeek)
                    Spacer(modifier = Modifier.height(25.dp))
                }
            )
        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun rememberFirstCompletelyVisibleMonth(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
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

private fun buildTitle(yearMonth: YearMonth): String {
    return "${
        yearMonth.month.getDisplayName(
            TextStyle.FULL_STANDALONE,
            Locale.getDefault()
        )
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    } ${yearMonth.year}"
}