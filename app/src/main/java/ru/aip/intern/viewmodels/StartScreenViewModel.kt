package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ru.aip.intern.navigation.Screen
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {

    private var apiKey: String? = null

    init {
        runBlocking {
            apiKey = dataStoreRepository.apiKey.first() ?: "hui"
        }
    }

    val startScreen: LiveData<Screen> = liveData {
        val screenName = if (apiKey == "" || apiKey == null) {
            Screen.Login
        } else {
            Screen.Internships
        }
        emit(screenName)
    }
}