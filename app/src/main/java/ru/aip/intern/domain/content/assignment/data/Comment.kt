package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import ru.aip.intern.serialization.UuidSerializer
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class Comment(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val author: String,
    val text: String,
    @Serializable(LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    val childComments: List<Comment>
)
