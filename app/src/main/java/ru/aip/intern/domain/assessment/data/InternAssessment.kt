package ru.aip.intern.domain.assessment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class InternAssessment(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val internName: String,
    val assessments: List<Assessment>
)
