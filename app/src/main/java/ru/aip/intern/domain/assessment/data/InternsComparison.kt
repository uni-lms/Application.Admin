package ru.aip.intern.domain.assessment.data

import kotlinx.serialization.Serializable

@Serializable
data class InternsComparison(
    val internsAssessment: List<InternAssessment>
)
