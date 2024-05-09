package ru.aip.intern.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.auth.service.AuthService
import ru.aip.intern.domain.internal.data.ReleaseInfo
import ru.aip.intern.domain.internal.service.InternalService
import ru.aip.intern.domain.notifications.service.NotificationsService
import ru.aip.intern.downloading.LocalFile
import ru.aip.intern.notifications.NotificationChannel
import ru.aip.intern.notifications.NotificationManager
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.storage.DataStoreRepository
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.MenuState
import ru.aip.intern.util.UiText
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val storage: DataStoreRepository,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val authService: AuthService,
    private val notificationsService: NotificationsService,
    private val titleManager: TitleManager,
    private val internalService: InternalService,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.menu))
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

            val whoamiResponse = authService.whoami()

            if (whoamiResponse.isSuccess) {
                _state.update {
                    it.copy(
                        whoAmIData = whoamiResponse.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(whoamiResponse.errorMessage!!)
            }

            val notificationsResponse = notificationsService.getUnreadAmount()

            if (notificationsResponse.isSuccess) {
                _state.update {
                    it.copy(
                        notificationsCount = notificationsResponse.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(notificationsResponse.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            storage.saveApiKey("")
        }
    }

    fun checkForUpdates(afterSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }

            val response = internalService.getLatestRelease(context.getString(R.string.app_version))

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        isShowSheet = true,
                        releaseInfo = response.value!!
                    )
                }
                afterSuccess()
            } else {
                snackbarMessageHandler.postMessage(UiText.StringResource(R.string.already_latest))
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    fun onSheetHide(afterHide: () -> Unit) {
        viewModelScope.launch {
            afterHide()
            _state.update {
                it.copy(
                    isShowSheet = false,
                    releaseInfo = null
                )
            }
        }
    }

    fun onDownload(releaseInfo: ReleaseInfo) {
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

            val outputFolder = File(
                context.getExternalFilesDir(null),
                "Updates"
            )
            outputFolder.mkdir()

            val outputFile = File(outputFolder, releaseInfo.fileName)

            val notification = notificationManager.buildNotification(
                NotificationChannel.Downloading,
                0,
                UiText.StringResource(R.string.downloading_file),
                UiText.DynamicText(_state.value.releaseInfo?.fileName!!),
                true
            )
            val notificationId = notificationManager.pushNotification(notification)

            val response = internalService.downloadFile(
                releaseInfo.downloadUrl,
                progressListener = { progress ->
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
                        UiText.DynamicText(_state.value.releaseInfo?.fileName!!),
                        true
                    )
                    notificationManager.pushNotification(notificationId, updatedNotification)

                })

            if (response.isSuccess) {
                outputFile.writeBytes(response.value!!)

                _state.update {
                    it.copy(
                        downloadButtonState = it.downloadButtonState.copy(
                            file = LocalFile(
                                file = outputFile,
                                name = releaseInfo.fileName,
                                mimeType = releaseInfo.contentType
                            )
                        )
                    )
                }

                delay(1000)
                val updatedNotification = notificationManager.buildNotification(
                    NotificationChannel.Downloading,
                    null,
                    UiText.StringResource(R.string.downloaded_file),
                    UiText.DynamicText(_state.value.releaseInfo?.fileName!!),
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

}