package ru.unilms.domain.file.model

import kotlinx.serialization.Serializable

@Serializable
data class FileContentInfo(
    val courseAbbreviation: String,
    val visibleName: String,
    val fileSize: String,
    val mimeType: String,
)
