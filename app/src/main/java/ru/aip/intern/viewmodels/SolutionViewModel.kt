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
import ru.aip.intern.ui.state.SolutionState
import java.util.UUID

@HiltViewModel(assistedFactory = SolutionViewModel.Factory::class)
class SolutionViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val assignmentService: AssignmentService,
    @Assisted val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): SolutionViewModel
    }

    private val _state = MutableStateFlow(SolutionState())
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
            val response = assignmentService.getSolution(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        solutionInfo = response.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    fun updateCommentText(newText: String) {
        _state.update {
            it.copy(
                commentText = newText
            )
        }
    }

    fun createComment(replyToCommentId: UUID?) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }

            val response =
                assignmentService.createComment(id, _state.value.commentText, replyToCommentId)

            if (response.isSuccess) {
                refresh()
                updateCommentText("")
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }


}
