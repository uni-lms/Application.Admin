package ru.unilms.domain.task.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.InstantSerializer
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.time.Instant
import java.util.UUID

@Serializable
data class Solution(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    @Serializable(InstantSerializer::class)
    val dateTime: Instant,
    val amountOfFiles: Int,
    val status: String,
)
