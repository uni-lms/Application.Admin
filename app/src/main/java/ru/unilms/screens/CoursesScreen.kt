package ru.unilms.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.components.courses.CourseCard
import ru.unilms.viewmodels.CoursesScreenViewModel

@Composable
fun CoursesScreen() {
    val viewModel = hiltViewModel<CoursesScreenViewModel>()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(10.dp)) {
        viewModel.courses.forEach {
            CourseCard(it)
        }
    }
}