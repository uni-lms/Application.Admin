package ru.aip.intern.ui.state

import ru.aip.intern.domain.events.data.CustomEvent
import ru.aip.intern.domain.events.data.CustomEventType
import java.time.LocalDateTime

data class EventState(
    val isRefreshing: Boolean = false,
    val eventData: CustomEvent = CustomEvent(
        title = "",
        startTimestamp = LocalDateTime.now(),
        endTimestamp = LocalDateTime.now(),
        link = null,
        type = CustomEventType.Meeting
    )
)