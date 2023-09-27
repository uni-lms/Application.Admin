package ru.unilms.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.ui.components.courses.CourseCard
import ru.unilms.viewmodels.CoursesScreenViewModel
import java.util.UUID

@Composable
fun CoursesScreen(goToCourseScreen: (UUID) -> Unit) {
    val viewModel = hiltViewModel<CoursesScreenViewModel>()

    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = viewModel.courses, itemContent = { item ->
            CourseCard(course = item) {
                goToCourseScreen(item.id)
            }
        })
    }
}