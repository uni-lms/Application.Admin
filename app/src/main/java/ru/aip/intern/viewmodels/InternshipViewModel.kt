package ru.aip.intern.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.domain.internships.data.UserRole
import ru.aip.intern.domain.internships.service.InternshipsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.state.InternshipState
import java.util.UUID

@HiltViewModel(assistedFactory = InternshipViewModel.Factory::class)
class InternshipViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val internshipsService: InternshipsService,
    @Assisted private val id: UUID
) :
    ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): InternshipViewModel
    }

    private val _state = MutableStateFlow(InternshipState())
    val state = _state.asStateFlow()

    var userRole = MutableLiveData(UserRole.Intern)
        private set

    init {
        refresh(id)
    }

    fun refresh(id: UUID) {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
            val response = internshipsService.getContent(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        contentData = response.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            val userRoleResponse = internshipsService.getRole(id)

            if (userRoleResponse.isSuccess) {
                userRole.value = userRoleResponse.value!!.name
            } else {
                snackbarMessageHandler.postMessage(userRoleResponse.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

}