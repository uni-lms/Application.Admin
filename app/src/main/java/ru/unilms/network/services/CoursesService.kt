package ru.unilms.network.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import ru.unilms.domain.model.courses.Course
import ru.unilms.domain.model.error.ErrorResponse
import ru.unilms.utils.enums.CourseType

interface CoursesService {

    suspend fun getEnrolled(type: CourseType): Response<List<Course>, ErrorResponse>

    companion object {
        val client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json()
            }
        }
    }
}