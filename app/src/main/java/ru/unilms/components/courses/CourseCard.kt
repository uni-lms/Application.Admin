package ru.unilms.components.courses

import androidx.compose.foundation.clickable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unilms.domain.model.courses.Course
import java.util.UUID

@Composable
fun CourseCard(course: Course, goToCourseScreen: () -> Unit) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = goToCourseScreen)
            .clip(MaterialTheme.shapes.medium),
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            headlineColor = MaterialTheme.colorScheme.onSurface,
            supportingColor = MaterialTheme.colorScheme.onSurface,
            overlineColor = MaterialTheme.colorScheme.onSurface
        ),
        shadowElevation = 8.dp,
        tonalElevation = 5.dp,
        headlineContent = {
            Text(
                text = course.name,
            )
        },
        trailingContent = {
            CircularProgressIndicator(
                progress = course.progress / 100,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.onSecondary
            )
        },
        supportingContent = { Text(text = course.tutors.joinToString()) },
        overlineContent = { Text(text = "${course.semester} семестр") }
    )
}

@Composable
@Preview
fun CourseCardPreview() {
    CourseCard(
        course = Course(
            UUID.randomUUID(),
            "ТП",
            35f,
            3,
            listOf("Вершинин В. В.")
        )
    ) {}
}