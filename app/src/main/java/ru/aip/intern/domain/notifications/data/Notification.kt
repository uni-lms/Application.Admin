package ru.aip.intern.domain.notifications.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class Notification(
    val title: String,
    val description: String?,
    @Serializable(LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    val triggerType: NotificationType,
    @Serializable(UuidSerializer::class)
    val triggerId: UUID,
    val isRead: Boolean
)
