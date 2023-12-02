package ru.unilms.domain.manage.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import ru.unilms.data.DataStore
import ru.unilms.domain.common.network.HttpClientFactory
import ru.unilms.domain.common.network.processResponse
import ru.unilms.domain.manage.model.Group
import ru.unilms.domain.manage.model.UpdateGroupRequest
import ru.unilms.domain.manage.network.ManageServiceImpl
import ru.unilms.domain.manage.view.form.ManageGroupForm
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ManageGroupViewModel @Inject constructor(@ApplicationContext val context: Context) :
    ViewModel() {

    private var store: DataStore = DataStore(context)
    private lateinit var service: ManageServiceImpl
    val form = ManageGroupForm()

    init {
        viewModelScope.launch {
            store.apiUri.collect {
                HttpClientFactory.baseUrl = it
            }
        }

        viewModelScope.launch {
            store.token.collect {
                service = ManageServiceImpl(it)
            }
        }
    }


    suspend fun getGroup(groupId: UUID): Group? {
        var group: Group? = null

        val response = service.getGroup(groupId)

        viewModelScope.launch {
            group = processResponse(response)
        }

        return group
    }

    suspend fun updateGroup(groupId: UUID) {
        val request = UpdateGroupRequest(
            groupId,
            form.name.state.value!!,
            form.currentSemester.state.value?.toInt() ?: 0,
            form.maxSemester.state.value?.toInt() ?: 0
        )
        service.updateGroup(request)
        Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()
    }

    fun initForm(group: Group) {
        form.name.state.value = group.name
        form.currentSemester.state.value = group.currentSemester.toString()
        form.maxSemester.state.value = group.maxSemester.toString()
    }

}
