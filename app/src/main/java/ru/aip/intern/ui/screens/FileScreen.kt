package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.FileViewModel
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FileScreen(
    title: MutableState<String>,
    fileId: UUID
) {

    val viewModel = hiltViewModel<FileViewModel, FileViewModel.Factory>(
        creationCallback = { factory -> factory.create(fileId) }
    )
    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refresh() }
    )

    LaunchedEffect(state.fileData) {
        title.value = state.fileData.title
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Scale,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.file_size)) },
                trailingContent = { Text(text = state.fileData.fileSize) }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.AttachFile,
                        contentDescription = null
                    )
                },
                headlineContent = { Text(text = stringResource(R.string.file_type)) },
                trailingContent = {
                    Text(
                        text = "Документ ${
                            state.fileData.extension.uppercase(
                                Locale.ROOT
                            ).replace(".", "")
                        }"
                    )
                }
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        viewModel.downloadFile()
                    }
                ) {
                    Text(text = stringResource(R.string.file_download))
                }
            }

        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}