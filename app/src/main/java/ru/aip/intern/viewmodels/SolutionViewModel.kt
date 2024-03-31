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
import ru.aip.intern.domain.content.assignment.data.SolutionInfo
import ru.aip.intern.domain.content.assignment.service.AssignmentService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import java.time.LocalDateTime
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

    val defaultSolution = SolutionInfo(
        author = "",
        createdAt = LocalDateTime.now(),
        link = null,
        files = emptyList(),
        comments = emptyList()
    )

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _solutionData = MutableLiveData(defaultSolution)
    val solutionData: LiveData<SolutionInfo> = _solutionData

    private val _commentText = MutableLiveData("")
    val commentText: LiveData<String> = _commentText

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _isRefreshing.value = true
            val response = assignmentService.getSolution(id)

            if (response.isSuccess) {
                _solutionData.value = response.value!!
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }

    fun updateCommentText(newText: String) {
        _commentText.value = newText
    }

    fun createComment(replyToCommentId: UUID?) {
        viewModelScope.launch {
            _isRefreshing.value = true

            val response =
                assignmentService.createComment(id, _commentText.value!!, replyToCommentId)

            if (response.isSuccess) {
                refresh()
                updateCommentText("")
            } else {
                snackbarMessageHandler.postMessage(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }


}
