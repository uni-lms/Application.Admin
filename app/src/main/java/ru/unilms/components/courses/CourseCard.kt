package ru.unilms.components.courses

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unilms.domain.model.courses.Course
import java.util.UUID

@Composable
fun CourseCard(course: Course) {

    var progress by remember {
        mutableFloatStateOf(0f)
    }

    val progressAnimate by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "course's progress"
    )

    LaunchedEffect(Unit) {
        progress = course.progress / 100
    }

    Surface(color = MaterialTheme.colorScheme.tertiaryContainer, shadowElevation = 5.dp) {
        ListItem(headlineContent = {
            Text(
                text = course.name,
            )
        },
            trailingContent = {
                CircularProgressIndicator(
                    progress = progressAnimate,
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            },
            supportingContent = { Text(text = course.tutors.joinToString()) },
            overlineContent = { Text(text = "${course.semester} семестр") }
        )
    }
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
    )
}