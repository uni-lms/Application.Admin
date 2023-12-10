package ru.unilms.domain.course.model

import android.net.Uri
import java.util.UUID

data class CreateFileRequest(
    val courseId: UUID,
    val blockId: UUID,
    val isVisibleToStudents: Boolean,
    val visibleName: String,
    val fileUri: Uri,
)