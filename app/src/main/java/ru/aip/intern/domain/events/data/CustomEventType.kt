package ru.aip.intern.domain.events.data

import ru.aip.intern.R
import ru.aip.intern.util.UiText

enum class CustomEventType(val title: UiText) {
    Meeting(UiText.StringResource(R.string.meeting))
}