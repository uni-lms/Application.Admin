package ru.unilms.domain.quiz.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.LocalDateTimeSerializer
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class AttemptInfo(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    @Serializable(LocalDateTimeSerializer::class)
    val startedAt: LocalDateTime,
    @Serializable(LocalDateTimeSerializer::class)
    val finishedAt: LocalDateTime?,
    val accruedPoints: Int,
)
