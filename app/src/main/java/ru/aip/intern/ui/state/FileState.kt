package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.file.data.FileInfo
import ru.aip.intern.ui.screens.DownloadButtonState
import java.util.UUID

data class FileState(
    val isRefreshing: Boolean = false,
    val downloadButtonState: DownloadButtonState = DownloadButtonState(),
    val fileData: FileInfo = FileInfo(
        title = "",
        fileName = "",
        fileSize = "",
        contentType = "",
        extension = "",
        id = UUID.randomUUID()
    )
)
