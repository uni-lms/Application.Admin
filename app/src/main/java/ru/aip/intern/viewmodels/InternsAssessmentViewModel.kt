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
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.InternsAssessmentState
import ru.aip.intern.util.UiText
import java.util.UUID

@HiltViewModel(assistedFactory = InternsAssessmentViewModel.Factory::class)
class InternsAssessmentViewModel @AssistedInject constructor(
    private val assessmentService: AssessmentService,
    private val titleManager: TitleManager,
    @Assisted private val id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): InternsAssessmentViewModel
    }

    private val _state = MutableStateFlow(InternsAssessmentState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.interns_assessment))
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