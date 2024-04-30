package ru.aip.intern.ui.managers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.aip.intern.R
import ru.aip.intern.util.UiText
import javax.inject.Inject

class TitleManager @Inject constructor() {

    private val _title = MutableStateFlow<UiText>(UiText.StringResource(R.string.app_name))
    val title = _title.asStateFlow()

    suspend fun update(newValue: UiText) {
        _title.emit(newValue)
    }
}