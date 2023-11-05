package ru.unilms.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.courses.FileContentInfo
import ru.unilms.network.services.CoursesServiceImpl
import ru.unilms.network.services.HttpClientFactory
import ru.unilms.utils.networking.processResponse
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TextViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

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

    suspend fun getFileContent(textId: UUID): String {
        var result: ByteArray? = null
        val response = service.getTextContent(textId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return String(result ?: byteArrayOf())
    }

    suspend fun getFileInfo(textId: UUID): FileContentInfo? {
        var result: FileContentInfo? = null
        val response = service.getTextContentInfo(textId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }

}