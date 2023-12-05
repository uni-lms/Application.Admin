package ru.unilms.domain.course.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.course.Block
import ru.unilms.domain.course.network.CoursesServiceImpl
import ru.unilms.domain.manage.model.Group
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(@ApplicationContext private val context: Context) :
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

    suspend fun getBlocks(): List<Block> {
        var result: List<Block>? = null

        val response = service.getBlocks()

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result ?: emptyList()
    }

    suspend fun getGroups(): List<Group> {
        var result: List<Group>? = null

        val response = service.getGroups()

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result ?: emptyList()
    }

    suspend fun createCourse() {

    }

}