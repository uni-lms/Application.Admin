package ru.unilms.domain.model.courses

import kotlinx.serialization.Serializable

@Serializable
data class CourseContent(val subjectAbbreviation: String, val blocks: List<CourseBlock>)
