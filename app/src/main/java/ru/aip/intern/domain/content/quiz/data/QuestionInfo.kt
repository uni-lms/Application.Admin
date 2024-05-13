package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class QuestionInfo(
    @Serializable(UuidSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val text: String = "",
    val isMultipleChoicesAllowed: Boolean = false,
    val choices: List<Choice> = emptyList(),
    val amountOfQuestions: Int = 0,
)
