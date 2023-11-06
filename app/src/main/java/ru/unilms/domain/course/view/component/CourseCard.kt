package ru.unilms.domain.course.view.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.unilms.R
import ru.unilms.domain.course.model.Course

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
        overlineContent = {
            Text(
                text = stringResource(
                    id = R.string.label_semester,
                    course.semester
                )
            )
        }
    )
}