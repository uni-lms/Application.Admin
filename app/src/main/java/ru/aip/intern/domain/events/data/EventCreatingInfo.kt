package ru.aip.intern.domain.events.data

import kotlinx.serialization.Serializable
import ru.aip.intern.domain.internships.data.Internship

@Serializable
data class EventCreatingInfo(
    val internships: List<Internship>,
    val users: List<User>,
    val eventTypes: List<EventTypeInfo>
)
