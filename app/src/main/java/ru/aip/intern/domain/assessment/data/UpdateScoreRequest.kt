package ru.aip.intern.domain.assessment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class UpdateScoreRequest(
    @Serializable(UuidSerializer::class)
    val internId: UUID,
    @Serializable(UuidSerializer::class)
    val criterionId: UUID,
    val score: Int
)
