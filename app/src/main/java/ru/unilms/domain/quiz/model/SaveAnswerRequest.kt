package ru.unilms.domain.quiz.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class SaveAnswerRequest(
    @Serializable(UUIDSerializer::class)
    val questionId: UUID,
    val choices: List<ChosenAnswer>,
)