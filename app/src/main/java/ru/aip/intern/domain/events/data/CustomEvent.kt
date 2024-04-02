package ru.aip.intern.domain.events.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class CustomEvent(
    val title: String,
    @Serializable(LocalDateTimeSerializer::class)
    val startTimestamp: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    val endTimestamp: LocalDateTime,
    val link: String?,
    val type: CustomEventType
)
