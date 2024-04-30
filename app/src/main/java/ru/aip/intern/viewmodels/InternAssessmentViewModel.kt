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
import ru.aip.intern.domain.assessment.service.AssessmentService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.InternAssessmentState
import ru.aip.intern.util.UiText
import java.util.UUID

@HiltViewModel(assistedFactory = InternAssessmentViewModel.Factory::class)
class InternAssessmentViewModel @AssistedInject constructor(
    private val assessmentService: AssessmentService,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val titleManager: TitleManager,
    @Assisted id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): InternAssessmentViewModel
    }

    private val _state = MutableStateFlow(InternAssessmentState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.interns_assessment))
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

            val response = assessmentService.getInternAssessment(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        assessmentData = response.value!!
                    )
                }

                if (response.value!!.internName.isNotBlank()) {
                    titleManager.update(UiText.DynamicText(response.value.internName))
                }

            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }
        }
    }

    fun updateScore(internId: UUID, criterionId: UUID, newScore: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefreshing = true
                )
            }

            val response = assessmentService.updateScore(internId, criterionId, newScore)

            if (response.isSuccess) {
                snackbarMessageHandler.postMessage(UiText.StringResource(R.string.update_succedeed))
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