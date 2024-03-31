package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Comment(
    val author: String,
    val text: String,
    @Serializable(LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    val childComments: List<Comment>
)
