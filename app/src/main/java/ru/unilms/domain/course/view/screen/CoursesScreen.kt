package ru.unilms.domain.course.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.Course
import ru.unilms.domain.course.util.enums.CourseType
import ru.unilms.domain.course.view.component.CourseCard
import ru.unilms.domain.course.viewmodel.CoursesViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(navigate: (Screens, UUID?) -> Unit, onComposing: (AppBarState) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<CoursesViewModel>()
    var courses: List<Course> by remember { mutableStateOf(emptyList()) }
    val refreshing = viewModel.isLoading

    fun updateCourses(type: CourseType = CourseType.Current) =
        coroutineScope.launch { courses = viewModel.loadCourses(type) }

    fun getCurrentCourseType(current: Boolean, archived: Boolean, upcoming: Boolean): CourseType {
        if (current) {
            return CourseType.Current
        }
        if (archived) {
            return CourseType.Archived
        }
        if (upcoming) {
            return CourseType.Upcoming
        }
        return CourseType.Current
    }

    var currentCoursesFilterChipStatus by remember { mutableStateOf(true) }
    var archivedCoursesFilterChipStatus by remember { mutableStateOf(false) }
    var upcomingCoursesFilterChipStatus by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoading,
        onRefresh = {
            updateCourses(
                getCurrentCourseType(
                    currentCoursesFilterChipStatus,
                    archivedCoursesFilterChipStatus,
                    upcomingCoursesFilterChipStatus
                )
            )
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
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(
                        selected = archivedCoursesFilterChipStatus,
                        onClick = {
                            currentCoursesFilterChipStatus = false
                            archivedCoursesFilterChipStatus = true
                            upcomingCoursesFilterChipStatus = false
                            updateCourses(CourseType.Archived)
                        },
                        label = { Text(stringResource(R.string.courses_filter_archived)) },
                        leadingIcon = if (archivedCoursesFilterChipStatus) {
                            {
                                Icon(
                                    imageVector = Icons.Outlined.Done,
                                    contentDescription = "Localized Description",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                    FilterChip(
                        selected = currentCoursesFilterChipStatus,
                        onClick = {
                            currentCoursesFilterChipStatus = true
                            archivedCoursesFilterChipStatus = false
                            upcomingCoursesFilterChipStatus = false
                            updateCourses(CourseType.Current)
                        },
                        label = { Text(stringResource(R.string.courses_filter_current)) },
                        leadingIcon = if (currentCoursesFilterChipStatus) {
                            {
                                Icon(
                                    imageVector = Icons.Outlined.Done,
                                    contentDescription = "Localized Description",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                    FilterChip(
                        selected = upcomingCoursesFilterChipStatus,
                        onClick = {
                            currentCoursesFilterChipStatus = false
                            archivedCoursesFilterChipStatus = false
                            upcomingCoursesFilterChipStatus = true
                            updateCourses(CourseType.Upcoming)
                        },
                        label = { Text(stringResource(R.string.courses_filter_upcoming)) },
                        leadingIcon = if (upcomingCoursesFilterChipStatus) {
                            {
                                Icon(
                                    imageVector = Icons.Outlined.Done,
                                    contentDescription = "Localized Description",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                }
            }
            items(items = courses, itemContent = { item ->
                CourseCard(course = item) {
                    navigate(Screens.Course, item.id)
                }
            })
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}