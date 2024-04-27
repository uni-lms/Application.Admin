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
import ru.aip.intern.domain.content.assignment.service.AssignmentService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.state.AssignmentState
import java.util.UUID

@HiltViewModel(assistedFactory = AssignmentViewModel.Factory::class)
class AssignmentViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val assignmentService: AssignmentService,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): AssignmentViewModel
    }

    private val _state = MutableStateFlow(AssignmentState())
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
            val response = assignmentService.getInfo(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        assignment = response.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            val solutionsResponse = assignmentService.getSolutionsInfo(id)

            if (solutionsResponse.isSuccess) {
                _state.update {
                    it.copy(
                        solutions = solutionsResponse.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(solutionsResponse.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }
}