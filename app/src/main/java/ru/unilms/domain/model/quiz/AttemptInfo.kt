package ru.unilms.domain.model.quiz

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ru.unilms.utils.enums.AttemptStatus
import ru.unilms.utils.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class AttemptInfo(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val startTimestamp: LocalDateTime,
    val status: AttemptStatus,
)
