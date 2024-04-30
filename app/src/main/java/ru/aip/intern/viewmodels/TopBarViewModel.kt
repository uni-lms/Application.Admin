package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.aip.intern.networking.ConnectivityObserver
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val networkState = connectivityObserver.observe()

}