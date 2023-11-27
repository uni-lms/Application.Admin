package ru.unilms.domain.manage.viewmodel

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
import ru.unilms.domain.manage.model.User
import ru.unilms.domain.manage.network.ManageServiceImpl
import ru.unilms.domain.manage.view.form.ManageUserForm
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(@ApplicationContext val context: Context) :
    ViewModel() {
    private var store: DataStore = DataStore(context)
    private lateinit var service: ManageServiceImpl
    val form = ManageUserForm()

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

    suspend fun getUser(userId: UUID): User? {
        var result: User? = null

        val response = service.getUser(userId)

        viewModelScope.launch {
            coroutineScope {
                result = processResponse(response)
            }
        }

        return result
    }

    fun initForm(user: User) {
        form.firstNameField.state.value = user.firstName
        form.lastNameField.state.value = user.lastName
        form.patronymicField.state.value = user.patronymic
        form.roleNameField.state.value = context.resources.getString(user.roleName.labelId)
    }
}