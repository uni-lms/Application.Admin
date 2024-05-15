package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable

@Serializable
data class AccruedPoints(
    val accrued: Int,
    val max: Int
)
