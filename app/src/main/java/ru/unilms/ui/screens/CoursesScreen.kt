package ru.unilms.ui.screens

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import ru.unilms.ui.components.courses.CourseCard
import ru.unilms.utils.enums.CourseType
import ru.unilms.viewmodels.CoursesScreenViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(navigate: (UniAppScreen, UUID?) -> Unit, onComposing: (AppBarState) -> Unit) {
    val viewModel = hiltViewModel<CoursesScreenViewModel>()
    var courses by remember { mutableStateOf(viewModel.loadCourses()) }
    val refreshing = viewModel.isLoading

    var currentCoursesFilterChipStatus by remember { mutableStateOf(true) }
    var archivedCoursesFilterChipStatus by remember { mutableStateOf(false) }
    var futureCoursesFilterChipStatus by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isLoading,
        onRefresh = viewModel::loadCourses
    )


    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
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
                            futureCoursesFilterChipStatus = false
                            courses = viewModel.loadCourses(CourseType.Archived)
                        },
                        label = { Text("Архивные") },
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
                            futureCoursesFilterChipStatus = false
                            courses = viewModel.loadCourses(CourseType.Current)
                        },
                        label = { Text("Текущие") },
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
                        selected = futureCoursesFilterChipStatus,
                        onClick = {
                            currentCoursesFilterChipStatus = false
                            archivedCoursesFilterChipStatus = false
                            futureCoursesFilterChipStatus = true
                            courses = viewModel.loadCourses(CourseType.Future)
                        },
                        label = { Text("Предстоящие") },
                        leadingIcon = if (futureCoursesFilterChipStatus) {
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
                    navigate(UniAppScreen.Course, item.id)
                }
            })
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}