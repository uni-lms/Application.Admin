package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.internships.data.Content
import ru.aip.intern.domain.internships.data.UserRole
import ru.aip.intern.domain.internships.service.InternshipsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
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

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _internshipData = MutableLiveData(Content(title = "", sections = emptyList()))
    val internshipData: LiveData<Content> = _internshipData

    var userRole = MutableLiveData(UserRole.Intern)
        private set

    init {
        refresh(id)
    }

    fun refresh(id: UUID) {

        viewModelScope.launch {
            _isRefreshing.value = true
            val response = internshipsService.getContent(id)

            if (response.isSuccess) {
                _internshipData.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            val userRoleResponse = internshipsService.getRole(id)

            if (userRoleResponse.isSuccess) {
                userRole.value = userRoleResponse.value!!.name
            } else {
                snackbarMessageHandler.postMessage(userRoleResponse.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }

}