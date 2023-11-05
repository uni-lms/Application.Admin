package ru.unilms.domain.model.courses

import kotlinx.serialization.Serializable

@Serializable
data class FileContentInfo(
    val courseAbbreviation: String,
    val visibleName: String
)