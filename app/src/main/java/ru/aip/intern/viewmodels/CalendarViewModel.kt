package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.domain.calendar.service.CalendarService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.CalendarState
import ru.aip.intern.util.UiText
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@HiltViewModel(assistedFactory = CalendarViewModel.Factory::class)
class CalendarViewModel @AssistedInject constructor(
    private val calendarService: CalendarService,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val titleManager: TitleManager,
    @Assisted var yearMonth: YearMonth
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(yearMonth: YearMonth): CalendarViewModel
    }

    private val _state = MutableStateFlow(CalendarState())
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
            val response = calendarService.getMonthEvents(yearMonth.year, yearMonth.monthValue)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        events = response.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            titleManager.update(UiText.DynamicText(buildTitle(yearMonth)))

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    fun getDayEvents(day: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isDayRefreshing = true
                )
            }
            val response = calendarService.getDayEvents(yearMonth.year, yearMonth.monthValue, day)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        dayEvents = response.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isDayRefreshing = false
                )
            }
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

}