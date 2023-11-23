package ru.unilms.domain.task.viewmodel

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
import ru.unilms.domain.task.model.SolutionRequestBody
import ru.unilms.domain.task.network.TaskServiceImpl
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SubmitAnswerViewModel @Inject constructor(@ApplicationContext val context: Context) :
    ViewModel() {
    private var store: DataStore = DataStore(context)
    private lateinit var service: TaskServiceImpl

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }

        viewModelScope.launch {
            store.token.collect {
                service = TaskServiceImpl(it)
            }
        }
    }

    suspend fun uploadFileSolution(taskId: UUID): Any? {
        var result: Any? = null

        val response = service.uploadSolution(
            taskId,
            SolutionRequestBody(false, listOf())
        )

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }
}
