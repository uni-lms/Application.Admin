package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.comments.CommentTree
import ru.aip.intern.ui.components.content.FileContentCard
import ru.aip.intern.util.format
import ru.aip.intern.viewmodels.SolutionViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SolutionScreen(title: MutableState<String>, id: UUID, navigate: (Screen, UUID) -> Unit) {

    title.value = "Решение"

    val viewModel = hiltViewModel<SolutionViewModel, SolutionViewModel.Factory>(
        creationCallback = { factory -> factory.create(id) }
    )

    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val solutionInfo = viewModel.solutionData.observeAsState(viewModel.defaultSolution)
    val uriHandler = LocalUriHandler.current

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh() }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            ListItem(
                leadingContent = {
                    Icon(imageVector = Icons.Outlined.AccessTime, contentDescription = null)

                },
                headlineContent = {
                    Text(text = "Отправлено")
                },
                trailingContent = {
                    Text(
                        text = solutionInfo.value.createdAt.format()
                    )
                }
            )
            ListItem(
                leadingContent = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Автор")
                },
                trailingContent = {
                    Text(text = solutionInfo.value.author)
                }
            )
            if (solutionInfo.value.link != null) {
                ListItem(
                    leadingContent = {
                        Icon(imageVector = Icons.Outlined.Link, contentDescription = null)
                    },
                    headlineContent = {
                        Text(text = "Решение ссылкой")
                    },
                    trailingContent = {
                        Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
                    },
                    modifier = Modifier.clickable {
                        uriHandler.openUri(solutionInfo.value.link!!)
                    }
                )
            }

            if (solutionInfo.value.files.isNotEmpty()) {
                Text(text = "Решение файлами")
            }

            solutionInfo.value.files.forEach {
                FileContentCard(content = it) { fileId ->
                    navigate(Screen.File, fileId)
                }
            }

            if (solutionInfo.value.comments.isNotEmpty()) {
                Text(text = "Комментарии")
            }

            CommentTree(comments = solutionInfo.value.comments) {

            }

        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}