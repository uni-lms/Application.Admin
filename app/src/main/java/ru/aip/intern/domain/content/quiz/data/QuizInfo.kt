package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
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
    val timeLimit: String?,
    val allowedAttempts: Int,
    val attempts: List<QuizPassAttempt>
)

fun QuizInfo.isTimed(): Boolean {
    return timeLimit != null
}
