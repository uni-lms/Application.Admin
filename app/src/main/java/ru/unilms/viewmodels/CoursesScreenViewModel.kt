package ru.unilms.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.courses.Course
import ru.unilms.network.HttpClientFactory
import ru.unilms.network.services.courses.CoursesServiceImpl
import ru.unilms.utils.enums.CourseType
import ru.unilms.utils.networking.processResponse
import javax.inject.Inject

@HiltViewModel
class CoursesScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    var isLoading = false
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

    suspend fun loadCourses(coursesType: CourseType = CourseType.Current): List<Course> {
        var result: List<Course>? = null

        val response = service.getEnrolled(coursesType)

        viewModelScope.launch {
            isLoading = true
            coroutineScope {
                result = processResponse(response)
            }
            isLoading = false
        }

        return result ?: emptyList()
    }

}
