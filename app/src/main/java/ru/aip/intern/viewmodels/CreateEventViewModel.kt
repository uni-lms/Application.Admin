package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.events.data.EventCreatingInfo
import ru.aip.intern.domain.events.data.User
import ru.aip.intern.domain.events.service.EventsService
import ru.aip.intern.domain.internships.data.Internship
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventsService: EventsService,
    private val snackbarMessageHandler: SnackbarMessageHandler
) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    val defaultEventCreatingInfo = EventCreatingInfo(
        listOf(
            Internship(UUID.randomUUID(), "fe"),
            Internship(UUID.randomUUID(), "fvdvfc"),
            Internship(UUID.randomUUID(), "fvewfd"),
        ), listOf(
            User(UUID.randomUUID(), "dvrvd"),
            User(UUID.randomUUID(), "fdvd"),
            User(UUID.randomUUID(), "vdsvcd"),
        )
    )

    private val _eventCreatingInfo = MutableLiveData(defaultEventCreatingInfo)
    val eventCreatingInfo: LiveData<EventCreatingInfo> = _eventCreatingInfo

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _isRefreshing.value = true

            val response = eventsService.getDataForEventCreating()

            if (response.isSuccess) {
                _eventCreatingInfo.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }

    }

    fun createEvent() {

    }

}