package ru.aip.intern.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ru.aip.intern.R
import ru.aip.intern.downloading.LocalFile
import ru.aip.intern.ui.screens.DownloadButtonState
import ru.aip.intern.ui.theme.AltenarInternshipTheme

@Composable
fun DownloadButton(
    state: DownloadButtonState,
    downloadTitle: String = stringResource(R.string.download_file),
    openTitle: String = stringResource(R.string.open_file),
    onDownloadStart: () -> Unit,
    onOpenFile: (file: LocalFile) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                if (!state.isDownloading && !state.isDownloaded) {
                    onDownloadStart()
                }

                if (!state.isDownloading && state.isDownloaded) {
                    onOpenFile(state.file!!)
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                if (!state.isDownloading && !state.isDownloaded) {
                    Icon(
                        Icons.Outlined.Download,
                        null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = downloadTitle)
                }

                if (state.isDownloading && !state.isDownloaded) {
                    LinearProgressIndicator(
                        progress = { state.downloadProgress },
                        color = ProgressIndicatorDefaults.linearTrackColor,
                        trackColor = ProgressIndicatorDefaults.linearColor
                    )
                }

                if (!state.isDownloading && state.isDownloaded) {
                    Icon(
                        Icons.Outlined.FileOpen,
                        null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = openTitle)
                }

            }
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun DownloadButtonPreview(
    @PreviewParameter(StatePreviewParameterProvider::class) state: DownloadButtonState,
) {
    AltenarInternshipTheme {
        Row(Modifier.width(350.dp)) {
            DownloadButton(
                state = state,
                onDownloadStart = {},
                onOpenFile = {}
            )
        }
    }
}

private class StatePreviewParameterProvider : PreviewParameterProvider<DownloadButtonState> {
    override val values: Sequence<DownloadButtonState>
        get() = sequenceOf(
            DownloadButtonState(
                isDownloading = false,
                isDownloaded = false,
                downloadProgress = 0f
            ),
            DownloadButtonState(
                isDownloading = true,
                isDownloaded = false,
                downloadProgress = 0f
            ),
            DownloadButtonState(
                isDownloading = true,
                isDownloaded = false,
                downloadProgress = 0.3f
            ),
            DownloadButtonState(
                isDownloading = false,
                isDownloaded = true,
                downloadProgress = 0f
            ),
        )
}