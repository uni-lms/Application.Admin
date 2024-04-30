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
import ru.aip.intern.domain.content.assignment.service.AssignmentService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.AssignmentState
import ru.aip.intern.util.UiText
import java.util.UUID

@HiltViewModel(assistedFactory = AssignmentViewModel.Factory::class)
class AssignmentViewModel @AssistedInject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val assignmentService: AssignmentService,
    private val titleManager: TitleManager,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): AssignmentViewModel
    }

    private val _state = MutableStateFlow(AssignmentState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.assignment))
        }
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

                if (response.value!!.title.isNotBlank()) {
                    titleManager.update(UiText.DynamicText(response.value.title))
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