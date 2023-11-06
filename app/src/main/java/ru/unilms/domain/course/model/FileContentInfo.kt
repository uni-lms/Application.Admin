package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable

@Serializable
data class FileContentInfo(
    val courseAbbreviation: String,
    val visibleName: String
)