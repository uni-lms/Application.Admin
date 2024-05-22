package ru.aip.intern.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.content.file.service.FileService
import ru.aip.intern.domain.internal.service.InternalService
import ru.aip.intern.downloading.LocalFile
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.notifications.NotificationChannel
import ru.aip.intern.notifications.NotificationManager
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.FileState
import ru.aip.intern.util.UiText
import java.io.File
import java.util.UUID

@HiltViewModel(assistedFactory = FileViewModel.Factory::class)
class FileViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val fileService: FileService,
    private val internalService: InternalService,
    private val authManager: AuthManager,
    private val titleManager: TitleManager,
    private val notificationManager: NotificationManager,
    @ApplicationContext private val context: Context,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): FileViewModel
    }

    private val _state = MutableStateFlow(FileState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.file))
        }
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
            val response = fileService.getFileInfo(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        fileData = response.value!!
                    )
                }

                if (response.value!!.title.isNotBlank()) {
                    titleManager.update(UiText.DynamicText(response.value.title))
                }

                val outputFile = getOutputFile()
                if (outputFile.exists()) {
                    _state.update {
                        it.copy(
                            downloadButtonState = it.downloadButtonState.copy(
                                isDownloaded = true,
                                file = LocalFile(
                                    file = outputFile,
                                    name = _state.value.fileData.fileName,
                                    mimeType = _state.value.fileData.contentType,
                                )
                            )
                        )
                    }
                }

            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    private fun buildDownloadUrl(): String {
        val url = "https://${HttpClientFactory.BASE_URL}/content/file/${id}/download"
        Log.d("downloadUrl", url)
        return url
    }

    fun downloadFile() {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    downloadButtonState = it.downloadButtonState.copy(
                        isDownloading = true,
                        isDownloaded = false,
                        downloadProgress = 0F
                    )
                )
            }

            val authHeaderValue = authManager.getAuthHeaderValue()
            val notification = notificationManager.buildNotification(
                NotificationChannel.Downloading,
                0,
                UiText.StringResource(R.string.downloading_file),
                UiText.DynamicText(_state.value.fileData.fileName),
                true
            )
            val notificationId = notificationManager.pushNotification(notification)

            val response =
                internalService.downloadFile(buildDownloadUrl(), progressListener = { progress ->
                    _state.update {
                        it.copy(
                            downloadButtonState = it.downloadButtonState.copy(
                                downloadProgress = progress
                            )
                        )
                    }

                    val updatedNotification = notificationManager.buildNotification(
                        NotificationChannel.Downloading,
                        (progress * 100).toInt(),
                        UiText.StringResource(R.string.downloading_file),
                        UiText.DynamicText(_state.value.fileData.fileName),
                        true
                    )
                    notificationManager.pushNotification(notificationId, updatedNotification)

                }, configureRequest = {
                    headers {
                        append(HttpHeaders.Authorization, authHeaderValue)
                    }
                })

            val outputFile = getOutputFile()

            if (response.isSuccess) {
                outputFile.writeBytes(response.value!!)

                _state.update {
                    it.copy(
                        downloadButtonState = it.downloadButtonState.copy(
                            file = LocalFile(
                                file = outputFile,
                                mimeType = _state.value.fileData.contentType,
                                name = _state.value.fileData.fileName
                            )
                        )
                    )
                }

                delay(1500)
                val updatedNotification = notificationManager.buildNotification(
                    NotificationChannel.Downloading,
                    null,
                    UiText.StringResource(R.string.downloaded_file),
                    UiText.DynamicText(_state.value.fileData.fileName),
                    false
                )
                notificationManager.pushNotification(notificationId, updatedNotification)

            }

            _state.update {
                it.copy(
                    downloadButtonState = it.downloadButtonState.copy(
                        isDownloading = false,
                        isDownloaded = true,
                        downloadProgress = 0F
                    )
                )
            }

        }
    }

    private fun getOutputFile(): File {
        val outputFolder = File(
            context.getExternalFilesDir(null),
            "Downloads"
        )
        outputFolder.mkdir()

        val fileName = "${_state.value.fileData.id}.${
            _state.value.fileData.fileName.substringAfterLast(
                ".",
                ""
            )
        }"

        val outputFile = File(outputFolder, fileName)
        return outputFile
    }


}