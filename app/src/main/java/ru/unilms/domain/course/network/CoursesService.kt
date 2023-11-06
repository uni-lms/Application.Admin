package ru.unilms.domain.course.network

import ru.unilms.domain.common.model.ErrorResponse
import ru.unilms.domain.common.network.Response
import ru.unilms.domain.course.model.Course
import ru.unilms.domain.course.model.CourseContent
import ru.unilms.domain.course.model.FileContentInfo
import ru.unilms.domain.course.util.enums.CourseType
import java.util.UUID

interface CoursesService {
    suspend fun getEnrolled(type: CourseType): Response<List<Course>, ErrorResponse>
    suspend fun getCourseContents(courseId: UUID): Response<CourseContent, ErrorResponse>
    suspend fun getTextContent(textId: UUID): Response<ByteArray, ErrorResponse>
    suspend fun getTextContentInfo(textId: UUID): Response<FileContentInfo, ErrorResponse>
}