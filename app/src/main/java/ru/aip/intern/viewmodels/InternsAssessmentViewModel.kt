package ru.aip.intern.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.assessment.data.InternsComparison
import ru.aip.intern.domain.assessment.service.AssessmentService
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


    var isRefreshing = MutableLiveData(false)
        private set
    var internsData = MutableLiveData(InternsComparison(emptyList()))
        private set

    init {
        refresh(id)
    }

    fun refresh(id: UUID) {
        viewModelScope.launch {
            isRefreshing.value = true

            val response = assessmentService.getInternsComparison(id)

            if (response.isSuccess) {
                internsData.value = response.value!!
            }

            isRefreshing.value = false
        }
    }

}