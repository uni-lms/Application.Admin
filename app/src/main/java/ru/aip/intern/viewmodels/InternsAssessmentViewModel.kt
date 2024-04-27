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
import ru.aip.intern.domain.assessment.service.AssessmentService
import ru.aip.intern.ui.state.InternsAssessmentState
import java.util.UUID

@HiltViewModel(assistedFactory = InternsAssessmentViewModel.Factory::class)
class InternsAssessmentViewModel @AssistedInject constructor(
    private val assessmentService: AssessmentService,
    @Assisted id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): InternsAssessmentViewModel
    }

    private val _state = MutableStateFlow(InternsAssessmentState())
    val state = _state.asStateFlow()

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

            val response = assessmentService.getInternsComparison(id)

            if (response.isSuccess) {
                _state.update {
                    it.copy(
                        assessmentsData = response.value!!
                    )
                }
            }

            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

}