package ru.aip.intern.viewmodels

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.calendar.data.EventType
import ru.aip.intern.domain.events.data.CreateEventRequest
import ru.aip.intern.domain.events.data.EventCreatingInfo
import ru.aip.intern.domain.events.data.User
import ru.aip.intern.domain.events.service.EventsService
import ru.aip.intern.domain.internships.data.Internship
import ru.aip.intern.navigation.Screen
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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

    @OptIn(ExperimentalMaterial3Api::class)
    private fun statesToLocalDateTime(
        datePickerState: DatePickerState,
        timePickerState: TimePickerState,
        userZoneId: ZoneId,
    ): LocalDateTime {
        val utcZone = ZoneId.of("UTC")
        return Instant
            .ofEpochMilli(datePickerState.selectedDateMillis!!)
            .atZone(utcZone)
            .withZoneSameInstant(userZoneId)
            .withHour(timePickerState.hour)
            .withMinute(timePickerState.minute)
            .withZoneSameInstant(utcZone)
            .toLocalDateTime()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun createEvent(
        eventTitle: String,
        eventLink: String?,
        datePickerState: DatePickerState,
        startTimePickerState: TimePickerState,
        endTimePickerState: TimePickerState,
        selectedInternships: SnapshotStateList<UUID>,
        selectedUsers: SnapshotStateList<UUID>,
        selectedEventType: Int?,
        userZoneId: ZoneId,
        navigate: (Screen, UUID) -> Unit
    ) {


        val request = CreateEventRequest(
            eventTitle,
            statesToLocalDateTime(datePickerState, startTimePickerState, userZoneId),
            statesToLocalDateTime(datePickerState, endTimePickerState, userZoneId),
            eventLink,
            selectedInternships.toList(),
            selectedUsers.toList(),
            EventType.entries[selectedEventType ?: 0].name
        )

        viewModelScope.launch {
            _isRefreshing.value = true

            val response = eventsService.createEvent(request)

            if (response.isSuccess) {
                navigate(Screen.Event, response.value!!.id)
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }

    }

}