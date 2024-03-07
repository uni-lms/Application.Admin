package ru.aip.intern.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.aip.intern.storage.DataStoreRepository
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MenuViewModel @Inject constructor(val storage: DataStoreRepository) : ViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    private val _unreadNotificationsCount = MutableLiveData(0)

    val unreadNotificationsCount: LiveData<Int> = _unreadNotificationsCount
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(300)
            _unreadNotificationsCount.value = Random.nextInt(0, 3)
            _isRefreshing.value = false
        }
    }

    fun logOut() {
        viewModelScope.launch {
            storage.saveApiKey("")
        }
    }

}