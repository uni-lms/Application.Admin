package ru.aip.intern.domain.content.quiz.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class Choice(
    @Serializable(UuidSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val text: String = "",
    val amountOfPoints: Int = 0,
    var isSelected: Boolean = false,
)
