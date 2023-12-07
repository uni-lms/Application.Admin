package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable
import ru.unilms.domain.common.serialization.UUIDSerializer
import java.util.UUID

@Serializable
data class FileResponse(
    val isVisibleToStudents: Boolean,
    val file: FileResponseFile,
)

@Serializable
data class FileResponseFile(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val visibleName: String,
)