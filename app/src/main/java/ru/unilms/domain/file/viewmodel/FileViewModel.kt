package ru.unilms.domain.file.viewmodel

import android.content.Context
import android.text.format.Formatter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.course.network.CoursesServiceImpl
import ru.unilms.domain.file.model.FileContentInfo
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private var store: DataStore = DataStore(context)
    private lateinit var service: CoursesServiceImpl

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }

        viewModelScope.launch {
            store.token.collect {
                service = CoursesServiceImpl(it)
            }
        }
    }

    suspend fun getFileInfo(fileId: UUID): FileContentInfo? {
        var result: FileContentInfo? = null

        val response = service.getFileContentInfo(fileId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }

    fun formatFileSize(fileSize: Long?): String? {
        if (fileSize != null) {
            return Formatter.formatFileSize(context, fileSize)
        }
        return null
    }
}