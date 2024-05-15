package ru.aip.intern.ui.state

import ru.aip.intern.domain.events.data.EventCreatingInfo

data class CreateEventState(
    val isRefreshing: Boolean = false,
    val eventCreatingInfo: EventCreatingInfo = EventCreatingInfo(
        listOf(), listOf()
    ),
    val formState: CreateEventFormState = CreateEventFormState()
)
