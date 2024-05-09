package ru.aip.intern.notifications

import ru.aip.intern.R
import ru.aip.intern.util.UiText

enum class NotificationChannel(val id: String, val channelName: UiText) {
    Downloading(
        "78f243a4-223d-4579-aa96-200de4d6ef8c",
        UiText.StringResource(R.string.channel_downloading)
    )
}