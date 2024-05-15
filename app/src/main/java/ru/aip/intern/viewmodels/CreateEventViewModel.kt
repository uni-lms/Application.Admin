package ru.aip.intern.viewmodels

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.calendar.data.EventType
import ru.aip.intern.domain.events.data.CreateEventRequest
import ru.aip.intern.domain.events.service.EventsService
import ru.aip.intern.navigation.Screen
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.CreateEventState
import ru.aip.intern.util.UiText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventsService: EventsService,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val titleManager: TitleManager
) : ViewModel() {

    private val _state = MutableStateFlow(CreateEventState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.create_event))
        }
        refresh()
    }

    fun updateFormStateTitle(title: String) {
        _state.update {
            it.copy(
                formState = it.formState.copy(
                    title = title
                )
            )
        }
    }

    fun updateFormStateLink(link: String) {
        _state.update {
            it.copy(
                formState = it.formState.copy(
                    link = link
                )
            )
        }
    }

    fun updateFormStateSelectedInternships(internships: List<UUID>) {
        _state.update {
            it.copy(
                formState = it.formState.copy(
                    selectedInternships = internships
                )
            )
        }
    }

    fun updateFormStateSelectedUsers(users: List<UUID>) {
        _state.update {
            it.copy(
                formState = it.formState.copy(
                    selectedUsers = users
                )
            )
        }
    }

    fun updateFormStateEventType(eventType: Int) {
        _state.update {
            it.copy(
                formState = it.formState.copy(
                    selectedEventType = eventType
                )
            )
        }
    }

    fun refresh() {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }

            val response = eventsService.getDataForEventCreating()

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        eventCreatingInfo = response.value!!
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
        datePickerState: DatePickerState,
        startTimePickerState: TimePickerState,
        endTimePickerState: TimePickerState,
        userZoneId: ZoneId,
        navigate: (Screen, UUID) -> Unit
    ) {


        val request = CreateEventRequest(
            _state.value.formState.title,
            statesToLocalDateTime(datePickerState, startTimePickerState, userZoneId),
            statesToLocalDateTime(datePickerState, endTimePickerState, userZoneId),
            _state.value.formState.link,
            _state.value.formState.selectedInternships,
            _state.value.formState.selectedUsers,
            EventType.entries[_state.value.formState.selectedEventType ?: 0].name
        )

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
            val response = eventsService.createEvent(request)

            if (response.isSuccess) {
                navigate(Screen.Event, response.value!!.id)
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

}