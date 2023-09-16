package ru.unilms.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.util.UUID

@Composable
fun CourseScreen(courseId: UUID) {
    Text(text = courseId.toString())
}