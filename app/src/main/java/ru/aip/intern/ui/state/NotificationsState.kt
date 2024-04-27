package ru.aip.intern.ui.state

import ru.aip.intern.domain.notifications.data.NotificationsList

data class NotificationsState(
    val isRefreshing: Boolean = false,
    val notificationsData: NotificationsList = NotificationsList(emptyList())
)