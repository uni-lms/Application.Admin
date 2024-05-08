package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aip.intern.R
import ru.aip.intern.domain.content.file.data.FileInfo
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.state.FileState
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FileScreen(
    state: FileState,
    onRefresh: () -> Unit,
    onDownloadFile: () -> Unit
) {


    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

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
                        text = stringResource(
                            R.string.document_type, state.fileData.extension.uppercase(
                                Locale.ROOT
                            ).replace(".", "")
                        )
                    )
                }
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = onDownloadFile,
                    enabled = !state.isDownloading
                ) {
                    if (state.isDownloading) {
                        LinearProgressIndicator(
                            progress = { state.downloadProgress },
                            modifier = Modifier.width(100.dp),
                        )
                    } else {
                        Text(text = stringResource(R.string.file_download))
                    }
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

@Preview
@Composable
private fun FileScreenPreview() {
    AltenarInternshipTheme {
        FileScreen(state = FileState(
            isRefreshing = false,
            fileData = FileInfo(
                id = UUID.randomUUID(),
                title = "Test file",
                fileName = "file.pdf",
                fileSize = "2 Mb",
                extension = "pdf",
                contentType = "application/pdf"
            )
        ), onRefresh = { }, onDownloadFile = { })
    }
}