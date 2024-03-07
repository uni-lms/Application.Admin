package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.aip.intern.domain.internships.data.Internship
import ru.aip.intern.domain.internships.service.InternshipsService
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class InternshipsViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _internshipData = MutableLiveData(emptyList<Internship>())
    val internshipData: LiveData<List<Internship>> = _internshipData

    private lateinit var service: InternshipsService

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage

    init {

        viewModelScope.launch {
            dataStoreRepository.apiKey.collect {
                service =
                    InternshipsService("eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibWVAZGFkeWFycmkucnUiLCJleHAiOjE3MDk1Njk1ODQsImlhdCI6MTcwOTU2NzE4NCwibmJmIjoxNzA5NTY3MTg0fQ.M1eGjQrPocQrYAHWKz1DWojAdtG1aobNG_R-wX708os")
            }
        }

        refresh()
    }

    fun refresh() {

        viewModelScope.launch {
            _isRefreshing.value = true
            val response = service.getEnrolled()

            if (response.isSuccess) {
                _internshipData.value = response.value!!
            } else {
                triggerSnackbar(response.errorMessage!!)
            }

            _isRefreshing.value = false
        }
    }

    private fun triggerSnackbar(message: String) {
        viewModelScope.launch {
            _snackbarMessage.emit(message)
        }
    }

}