package ru.unilms.domain.quiz.view.screen

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import ru.unilms.domain.quiz.util.enums.AttemptStatus
import java.util.UUID

@Serializable
data class AttemptInfo(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val startTimestamp: LocalDateTime,
    val status: AttemptStatus,
)
