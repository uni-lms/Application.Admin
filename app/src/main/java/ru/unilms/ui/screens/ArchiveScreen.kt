package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.ui.components.courses.CourseCard
import ru.unilms.viewmodels.CoursesScreenViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArchiveScreen(goToCourseScreen: (UUID) -> Unit) {
    val viewModel = hiltViewModel<CoursesScreenViewModel>()

    val courses by remember { mutableStateOf(viewModel.loadCourses()) }
    val refreshing = viewModel.isLoading

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoading,
        onRefresh = viewModel::loadCourses
    )

    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(items = courses, itemContent = { item ->
                CourseCard(course = item) {
                    goToCourseScreen(item.id)
                }
            })
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}