package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable

@Serializable
data class SolutionsList(
    val solutions: List<SolutionDto>
)