package ru.aip.intern.domain.content.quiz

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.DurationSerializer
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class QuizInfo(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val title: String,
    val description: String?,
    @Serializable(LocalDateTimeSerializer::class)
    val availableUntil: LocalDateTime,
    @Serializable(DurationSerializer::class)
    val timeLimit: Duration,
    val allowedAttempts: Int,
)
