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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import ru.aip.intern.R
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.state.AssignmentState
import ru.aip.intern.util.format
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AssignmentScreen(
    state: AssignmentState,
    onRefresh: () -> Unit,
    onFileClick: () -> Unit,
    onSolutionClick: (UUID) -> Unit
) {


    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            if (state.assignment.description != null) {
                Text(text = state.assignment.description)
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
                        text = state.assignment.deadline.format()
                    )
                }
            )
            if (state.assignment.fileId != null) {
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
                        onFileClick()
                    }
                )
            }

            if (state.solutions.solutions.isNotEmpty()) {
                Text(text = stringResource(R.string.solutions))
            }

            state.solutions.solutions.forEachIndexed { ind, it ->
                ListItem(
                    modifier = Modifier.clickable {
                        onSolutionClick(it.id)
                    },
                    headlineContent = {
                        if (it.authorName == null) {
                            Text(
                                text = stringResource(
                                    R.string.solution_attempt,
                                    ind + 1
                                )
                            )
                        } else {
                            Text(
                                text = it.authorName
                            )
                        }
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
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}