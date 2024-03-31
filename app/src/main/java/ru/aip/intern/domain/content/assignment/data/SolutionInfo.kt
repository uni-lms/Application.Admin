package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable
import ru.aip.intern.domain.internships.data.FileContentItem
import ru.aip.intern.serialization.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class SolutionInfo(
    @Serializable(LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,

    val author: String,

    val link: String?,

    val files: List<FileContentItem>,

    val comments: List<Comment>
)
