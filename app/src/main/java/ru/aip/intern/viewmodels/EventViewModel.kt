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
import ru.aip.intern.domain.events.data.CustomEvent
import ru.aip.intern.domain.events.data.CustomEventType
import ru.aip.intern.domain.events.service.EventsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.time.LocalDateTime
import java.util.UUID

@HiltViewModel(assistedFactory = EventViewModel.Factory::class)
class EventViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val eventsService: EventsService,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): EventViewModel
    }

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    val defaultData = CustomEvent(
        title = "",
        startTimestamp = LocalDateTime.now(),
        endTimestamp = LocalDateTime.now(),
        link = null,
        type = CustomEventType.Meeting
    )

    private val _data = MutableLiveData(defaultData)
    val data: LiveData<CustomEvent> = _data

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true

            val response = eventsService.getEvent(id)

            if (response.isSuccess) {
                _data.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }
}