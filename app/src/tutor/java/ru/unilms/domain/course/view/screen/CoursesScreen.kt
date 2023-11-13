package ru.unilms.domain.course.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.CourseTutor
import ru.unilms.domain.course.view.component.CourseCard
import ru.unilms.domain.course.viewmodel.CoursesViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoursesScreen(navigate: (Screens, UUID?) -> Unit, onComposing: (AppBarState) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<CoursesViewModel>()
    var courses: List<CourseTutor> by remember { mutableStateOf(emptyList()) }
    val refreshing = viewModel.isLoading

    fun updateCourses() =
        coroutineScope.launch { courses = viewModel.loadCourses() }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoading,
        onRefresh = {
            updateCourses()
        }
    )

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
        updateCourses()
    }


    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items = courses, itemContent = { item ->
                CourseCard(course = item) {
                    navigate(Screens.Course, item.id)
                }
            })
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}