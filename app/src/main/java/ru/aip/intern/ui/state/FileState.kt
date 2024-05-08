package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.file.data.FileInfo
import java.util.UUID

data class FileState(
    val isRefreshing: Boolean = false,
    val isDownloading: Boolean = false,
    val downloadProgress: Float = 0F,
    val fileData: FileInfo = FileInfo(
        title = "",
        fileName = "",
        fileSize = "",
        contentType = "",
        extension = "",
        id = UUID.randomUUID()
    )
)
