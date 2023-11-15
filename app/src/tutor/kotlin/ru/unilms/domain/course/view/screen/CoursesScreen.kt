package ru.unilms.domain.course.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
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
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.CourseTutor
import ru.unilms.domain.course.view.component.CourseCard
import ru.unilms.domain.course.viewmodel.CoursesViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoursesScreen(
    navigate: (Screens, UUID?) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<CoursesViewModel>()
    var courses: List<CourseTutor> by remember { mutableStateOf(emptyList()) }
    var isRefreshing by remember { mutableStateOf(true) }

    fun updateCourses() {
        isRefreshing = true
        coroutineScope.launch { courses = viewModel.loadCourses() }
        isRefreshing = false
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            updateCourses()
        }
    )

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            ),
            FabState(
                fab = {
                    FloatingActionButton(onClick = { navigate(Screens.CreateCourse, null) }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                    }
                }
            )
        )
        updateCourses()
    }

    Box(
        Modifier
            .pullRefresh(pullRefreshState)
    ) {
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
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}