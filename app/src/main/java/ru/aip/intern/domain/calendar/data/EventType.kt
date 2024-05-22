package ru.aip.intern.domain.calendar.data

import kotlinx.serialization.Serializable
import ru.aip.intern.R
import ru.aip.intern.serialization.EventTypeSerializer
import ru.aip.intern.util.UiText

@Serializable(EventTypeSerializer::class)
enum class EventType(val label: UiText) {
    Deadline(UiText.StringResource(R.string.deadline)),
    Meeting(UiText.StringResource(R.string.meeting)),
}