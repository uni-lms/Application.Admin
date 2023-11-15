package ru.unilms.domain.quiz.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class QuestionInfo(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val quizTitle: String,
    val questionTitle: String,
    val sequenceNumber: Int,
    val amountOfQuestions: Int,
    val isMultipleChoicesAllowed: Boolean,
    val choices: List<QuestionChoice>,
    val selectedChoices: List<QuestionChoice>,
)
