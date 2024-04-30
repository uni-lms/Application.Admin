package ru.aip.intern.viewmodels

import android.app.DownloadManager
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.auth.AuthManager
import ru.aip.intern.domain.content.file.service.FileService
import ru.aip.intern.networking.HttpClientFactory
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.FileState
import ru.aip.intern.util.UiText
import java.util.UUID

@HiltViewModel(assistedFactory = FileViewModel.Factory::class)
class FileViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val fileService: FileService,
    private val downloadManager: DownloadManager,
    private val authManager: AuthManager,
    private val titleManager: TitleManager,
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
        val url = "https://${HttpClientFactory.baseUrl}/content/file/${id}/download"
        Log.d("downloadUrl", url)
        return url
    }

    fun downloadFile() {
        viewModelScope.launch {
            val request = DownloadManager.Request(buildDownloadUrl().toUri())
                .setMimeType(_state.value.fileData.contentType)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(_state.value.fileData.fileName)
                .addRequestHeader(HttpHeaders.Authorization, authManager.getAuthHeaderValue())
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "AipDownloads/${_state.value.fileData.fileName}"
                )

            downloadManager.enqueue(request)
        }
    }


}