package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.time.LocalDateTime
import java.util.UUID


@Serializable
data class SolutionDto(
    @Serializable(UuidSerializer::class)
    val id: UUID,

    @Serializable(LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,

    val amountOfComments: Int
)
