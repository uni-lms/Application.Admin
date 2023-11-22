package ru.unilms.domain.journal.viewmodel

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
import ru.unilms.domain.course.network.CoursesServiceImpl
import ru.unilms.domain.journal.model.JournalDto
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {
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

    suspend fun getJournal(courseId: UUID): JournalDto? {
        var result: JournalDto? = null

        val response = service.getJournal(courseId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }
}