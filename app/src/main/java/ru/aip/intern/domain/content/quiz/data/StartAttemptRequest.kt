package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class StartAttemptRequest(
    @Serializable(UuidSerializer::class)
    val quiz: UUID
)