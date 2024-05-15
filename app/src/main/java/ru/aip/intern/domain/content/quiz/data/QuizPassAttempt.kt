package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class QuizPassAttempt(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    @Serializable(LocalDateTimeSerializer::class)
    val startedAt: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    val finishedAt: LocalDateTime?,
    val accruedPoints: AccruedPoints,
    val internName: String,
)

fun QuizPassAttempt.isFinished(): Boolean {
    return finishedAt != null
}

fun QuizPassAttempt.isNotFinished(): Boolean {
    return !isFinished()
}