package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.util.format
import ru.aip.intern.viewmodels.AssignmentViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AssignmentScreen(
    title: MutableState<String>,
    assignmentId: UUID,
    navigate: (Screen, UUID) -> Unit
) {
    val viewModel = hiltViewModel<AssignmentViewModel, AssignmentViewModel.Factory>(
        creationCallback = { factory -> factory.create(assignmentId) }
    )
    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val assignmentData = viewModel.assignmentData.observeAsState(viewModel.defaultContent)
    val solutionsData = viewModel.solutionsData.observeAsState(viewModel.defaultSolutions)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh() }
    )

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (assignmentData.value.title.isEmpty()) {
            title.value = context.getString(R.string.assignment)
        }
    }

    LaunchedEffect(assignmentData.value.title) {
        if (assignmentData.value.title.isNotEmpty()) {
            title.value = assignmentData.value.title
        }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            if (assignmentData.value.description != null) {
                Text(text = assignmentData.value.description!!)
            }
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Scale,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.deadline)) },
                trailingContent = {
                    Text(
                        text = assignmentData.value.deadline.format()
                    )
                }
            )
            if (assignmentData.value.fileId != null) {
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.AttachFile,
                            contentDescription = null
                        )
                    },
                    headlineContent = { Text(text = stringResource(R.string.assingment_as_file)) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable {
                        navigate(Screen.File, assignmentData.value.fileId!!)
                    }
                )
            }

            if (solutionsData.value.solutions.isNotEmpty()) {
                Text(text = stringResource(R.string.solutions))
            }

            solutionsData.value.solutions.forEachIndexed { ind, it ->
                ListItem(
                    modifier = Modifier.clickable {
                        navigate(Screen.Solution, it.id)
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(
                                R.string.solution_attempt,
                                ind + 1
                            )
                        )
                    },
                    supportingContent = {
                        Text(
                            text = it.createdAt.format()
                        )
                    },
                    trailingContent = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (it.amountOfComments > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ) {
                                    Text(
                                        text = pluralStringResource(
                                            R.plurals.amount_of_comments,
                                            it.amountOfComments,
                                            it.amountOfComments
                                        )
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = null
                            )

                        }
                    }
                )
            }


        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}