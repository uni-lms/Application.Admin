package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.ui.components.courses.CourseCard
import ru.unilms.viewmodels.CoursesScreenViewModel
import java.util.UUID

@Composable
fun ArchiveScreen(goToCourseScreen: (UUID) -> Unit) {
    val viewModel = hiltViewModel<CoursesScreenViewModel>()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(10.dp)) {
        viewModel.courses.forEach {
            CourseCard(it) { goToCourseScreen(it.id) }
        }
    }
}