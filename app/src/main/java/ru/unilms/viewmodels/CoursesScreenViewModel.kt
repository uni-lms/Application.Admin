package ru.unilms.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.model.courses.Course
import ru.unilms.network.services.CoursesService
import ru.unilms.network.services.CoursesServiceImpl
import ru.unilms.utils.enums.CourseType
import ru.unilms.utils.networking.processResponse
import javax.inject.Inject

@HiltViewModel
class CoursesScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    var isLoading = false
    private var store: DataStore? = null
    private var baseUrl = ""
    private var token = ""

    init {
        store = DataStore(context)
        viewModelScope.launch {
            store!!.apiUri.collect {
                baseUrl = it
            }
        }

        viewModelScope.launch {
            store!!.token.collect {
                token = it
            }
        }
    }

    fun loadCourses(coursesType: CourseType = CourseType.Current): List<Course> {
        isLoading = true
        var result = emptyList<Course>()

        viewModelScope.launch {
            CoursesService.client.use { client ->
                val service = CoursesServiceImpl(client, baseUrl, token)
                processResponse(service.getEnrolled(coursesType),
                    onSuccess = {
                        result = it
                    },
                    onError = {
                        val message =
                            if (it != null) "Произошла ошибка: ${it.reason}"
                            else "Произошла неизвестная ошибка"
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        isLoading = false
        return result
    }

}
