package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.internships.service.InternshipsService
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.managers.TitleManager
import ru.aip.intern.ui.state.InternshipsState
import ru.aip.intern.util.UiText
import javax.inject.Inject

@HiltViewModel
class InternshipsViewModel @Inject constructor(
    private val snackbarMessageHandler: SnackbarMessageHandler,
    private val internshipsService: InternshipsService,
    private val titleManager: TitleManager
) :
    ViewModel() {

    private val _state = MutableStateFlow(InternshipsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            titleManager.update(UiText.StringResource(R.string.internships))
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
            val enrolledResponse = internshipsService.getEnrolled()

            if (enrolledResponse.isSuccess) {
                _state.update {
                    it.copy(
                        internships = enrolledResponse.value!!
                    )
                }
            } else {
                snackbarMessageHandler.postMessage(enrolledResponse.errorMessage!!)
            }

            if (_state.value.internships.isEmpty()) {
                val ownedResponse = internshipsService.getOwned()

                if (ownedResponse.isSuccess) {
                    _state.update {
                        it.copy(
                            internships = ownedResponse.value!!
                        )
                    }
                } else {
                    snackbarMessageHandler.postMessage(ownedResponse.errorMessage!!)
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