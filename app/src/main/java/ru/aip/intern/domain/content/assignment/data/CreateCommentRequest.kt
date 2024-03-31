package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.serialization.UuidSerializer
import java.util.UUID

@Serializable
data class CreateCommentRequest(
    val text: String,
    @Serializable(UuidSerializer::class)
    val parentCommentId: UUID?
)
