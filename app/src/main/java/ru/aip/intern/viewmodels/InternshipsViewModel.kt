package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class InternshipsViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {

    fun dummyApiKey() {
        viewModelScope.launch {
            dataStoreRepository.saveApiKey("123")
        }
    }

}