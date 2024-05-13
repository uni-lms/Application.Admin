package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class SaveAnswerRequest(
    @Serializable(UuidSerializer::class)
    val question: UUID,
    val selectedChoices: List<@Serializable(UuidSerializer::class) UUID>
)
