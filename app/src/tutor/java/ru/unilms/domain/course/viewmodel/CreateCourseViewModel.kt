package ru.unilms.domain.course.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.course.model.Block
import ru.unilms.domain.course.model.CreateCourseRequest
import ru.unilms.domain.course.network.CoursesServiceImpl
import ru.unilms.domain.course.view.form.CreateCourseForm
import ru.unilms.domain.manage.model.Group
import java.util.UUID
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

    suspend fun createCourse(form: CreateCourseForm, navigate: (UUID) -> Unit) {
        val request = CreateCourseRequest(
            form.nameField.state.value!!,
            form.abbreviationField.state.value!!,
            form.semesterField.state.value!!.toInt(),
            listOf(form.groupsField.state.value!!.id.toString())
        )

        val response = service.createCourse(request)

        viewModelScope.launch {
            coroutineScope {
                val result = processResponse(response)
                result?.let { navigate(it.id) }
            }
        }
        Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()
    }

}