package ru.unilms.domain.model.courses

import java.util.UUID

data class Course(
    val id: UUID,
    val name: String,
    val progress: Float,
    val semester: Int,
    val tutors: List<String>
) {
}