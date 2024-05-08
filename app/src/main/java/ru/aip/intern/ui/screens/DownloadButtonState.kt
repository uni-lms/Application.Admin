package ru.aip.intern.ui.screens

import ru.aip.intern.downloading.LocalFile

data class DownloadButtonState(
    val isDownloading: Boolean = false,
    val isDownloaded: Boolean = false,
    val downloadProgress: Float = 0F,
    val file: LocalFile? = null
)
