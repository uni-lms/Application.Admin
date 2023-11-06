package ru.unilms.domain.model.quiz

import kotlinx.serialization.Serializable
import ru.unilms.utils.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class QuestionChoice(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val title: String
)
