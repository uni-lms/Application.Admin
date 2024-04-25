package ru.aip.intern.domain.assessment.data

import kotlinx.serialization.Serializable

@Serializable
data class InternAssessment(
    val internName: String,
    val assessments: List<Assessment>
)
