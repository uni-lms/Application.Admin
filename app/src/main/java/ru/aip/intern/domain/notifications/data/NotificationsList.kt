package ru.aip.intern.domain.notifications.data

import kotlinx.serialization.Serializable

@Serializable
data class NotificationsList(
    val notifications: List<Notification>
)
