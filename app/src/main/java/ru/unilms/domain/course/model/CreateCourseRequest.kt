package ru.unilms.domain.course.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCourseRequest(
    val name: String,
    val abbreviation: String,
    val semester: Int,
    val assignedGroups: List<String>,
)
