package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable

@Serializable
data class TextContentInfo(
    val courseAbbreviation: String,
    val visibleName: String
)