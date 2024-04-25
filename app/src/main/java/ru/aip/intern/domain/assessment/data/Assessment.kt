package ru.aip.intern.domain.assessment.data

import kotlinx.serialization.Serializable

@Serializable
data class Assessment(
    val title: String,
    val description: String?,
    val weight: Double,
    val score: Int?
)