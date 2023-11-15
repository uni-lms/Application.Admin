package ru.unilms.domain.file.model

import kotlinx.serialization.Serializable

@Serializable
data class FileContentInfo(
    val courseAbbreviation: String,
    val visibleName: String,
    val fileSize: Long,
    val extension: String,
    val fileId: String,
    val mimeType: String,
    val fileName: String,
)
