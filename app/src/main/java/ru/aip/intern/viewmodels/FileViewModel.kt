package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.content.file.data.FileInfo
import ru.aip.intern.domain.content.file.service.FileService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.util.UUID

@HiltViewModel(assistedFactory = FileViewModel.Factory::class)
class FileViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val fileService: FileService,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): FileViewModel
    }

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    val defaultContent = FileInfo(
        title = "",
        fileName = "",
        fileSize = "",
        contentType = "",
        extension = "",
        id = id
    )

    private val _fileData = MutableLiveData(defaultContent)
    val fileData: LiveData<FileInfo> = _fileData

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _isRefreshing.value = true
            val response = fileService.getFileInfo(id)

            if (response.isSuccess) {
                _fileData.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }

}