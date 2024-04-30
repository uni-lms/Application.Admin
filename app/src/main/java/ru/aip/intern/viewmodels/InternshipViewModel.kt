package ru.aip.intern.viewmodels

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
import ru.aip.intern.R
import ru.aip.intern.domain.internships.service.InternshipsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.InternshipState
import ru.aip.intern.util.UiText
import java.util.UUID

@HiltViewModel(assistedFactory = InternshipViewModel.Factory::class)
class InternshipViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val internshipsService: InternshipsService,
    private val titleManager: TitleManager,
    @Assisted private val id: UUID
) :
    ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): InternshipViewModel
    }

    private val _state = MutableStateFlow(InternshipState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.internship))
        }
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

                if (response.value!!.title.isNotBlank()) {
                    titleManager.update(UiText.DynamicText(response.value.title))
                }

            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            val userRoleResponse = internshipsService.getRole(id)

            if (userRoleResponse.isSuccess) {
                _state.update {
                    it.copy(
                        userRole = userRoleResponse.value!!.name
                    )
                }
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