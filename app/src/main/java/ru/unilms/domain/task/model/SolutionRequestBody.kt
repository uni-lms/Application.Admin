package ru.unilms.domain.task.model

import java.io.File

data class SolutionRequestBody(
    val isReadyForCheck: Boolean,
    val files: List<File>,
)