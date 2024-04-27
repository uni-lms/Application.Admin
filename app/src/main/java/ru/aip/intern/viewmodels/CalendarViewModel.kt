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
import ru.aip.intern.ui.state.CalendarState
import java.time.YearMonth

@HiltViewModel(assistedFactory = CalendarViewModel.Factory::class)
class CalendarViewModel @AssistedInject constructor(
    private val calendarService: CalendarService,
    private val snackbarMessageHandler: SnackbarMessageHandler,
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

}