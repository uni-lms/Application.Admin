package ru.unilms.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.courses.CourseContent
import ru.unilms.network.HttpClientFactory
import ru.unilms.network.services.courses.CoursesServiceImpl
import ru.unilms.utils.networking.processResponse
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CourseScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
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

    suspend fun getCourseContent(courseId: UUID): CourseContent? {
        var result: CourseContent? = null

        val response = service.getCourseContents(courseId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }

}