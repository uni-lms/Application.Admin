package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.calendar.data.MonthEvents
import ru.aip.intern.domain.calendar.service.CalendarService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.time.LocalDateTime
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

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val now = LocalDateTime.now()
    val defaultContent = MonthEvents(
        year = now.year,
        month = now.monthValue,
        days = emptyList()
    )

    private val _data = MutableLiveData(defaultContent)
    val data: LiveData<MonthEvents> = _data

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _isRefreshing.value = true
            val response = calendarService.getMonthEvents(yearMonth.year, yearMonth.monthValue)

            if (response.isSuccess) {
                _data.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }

}