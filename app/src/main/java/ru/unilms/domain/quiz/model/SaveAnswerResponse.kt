package ru.unilms.domain.quiz.model

import kotlinx.serialization.Serializable

@Serializable
data class SaveAnswerResponse(
    val questionTitle: String,
    val amountOfPoints: Int,
    val maximumPoints: Int,
)
