package ru.unilms.network.services

import ru.unilms.domain.model.courses.Course
import ru.unilms.domain.model.courses.CourseContent
import ru.unilms.domain.model.error.ErrorResponse
import ru.unilms.utils.enums.CourseType
import java.util.UUID

interface CoursesService {
    suspend fun getEnrolled(type: CourseType): Response<List<Course>, ErrorResponse>
    suspend fun getCourseContents(courseId: UUID): Response<CourseContent, ErrorResponse>
}