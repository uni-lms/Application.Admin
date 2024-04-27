package ru.aip.intern.ui.state

import java.util.UUID

data class CreateEventFormState(
    val title: String = "",
    val link: String = "",
    val selectedInternships: List<UUID> = emptyList(),
    val selectedUsers: List<UUID> = emptyList(),
    val selectedEventType: Int? = null,
)
