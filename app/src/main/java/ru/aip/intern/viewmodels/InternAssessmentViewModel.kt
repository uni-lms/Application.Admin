package ru.aip.intern.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.domain.assessment.data.InternAssessment
import ru.aip.intern.domain.assessment.service.AssessmentService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.util.UUID

@HiltViewModel(assistedFactory = InternAssessmentViewModel.Factory::class)
class InternAssessmentViewModel @AssistedInject constructor(
    private val assessmentService: AssessmentService,
    private val snackbarMessageHandler: SnackbarMessageHandler,
    @Assisted id: UUID
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: UUID): InternAssessmentViewModel
    }

    var isRefreshing = MutableLiveData(false)
        private set
    val defaultContent = InternAssessment(UUID.randomUUID(), "", emptyList())
    var internData = MutableLiveData(defaultContent)
        private set

    init {
        refresh(id)
    }

    fun refresh(id: UUID) {
        viewModelScope.launch {
            isRefreshing.value = true

            val response = assessmentService.getInternAssessment(id)

            if (response.isSuccess) {
                internData.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            isRefreshing.value = false
        }
    }

    fun updateScore(internId: UUID, criterionId: UUID, newScore: Int) {
        viewModelScope.launch {
            isRefreshing.value = true

            val response = assessmentService.updateScore(internId, criterionId, newScore)

            if (response.isSuccess) {
                snackbarMessageHandler.postMessage("Успешно обновлено")
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            isRefreshing.value = false
        }
    }

}