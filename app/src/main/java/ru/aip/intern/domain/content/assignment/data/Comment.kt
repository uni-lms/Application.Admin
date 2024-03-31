package ru.aip.intern.domain.content.assignment.data

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val author: String,
    val text: String,
    val childComments: List<Comment>
)
