package ru.aip.intern.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.aip.intern.ui.managers.TitleManager
import javax.inject.Inject

@HiltViewModel
class AipAppViewModel @Inject constructor(private val titleManager: TitleManager) : ViewModel() {

    val title = titleManager.title

}