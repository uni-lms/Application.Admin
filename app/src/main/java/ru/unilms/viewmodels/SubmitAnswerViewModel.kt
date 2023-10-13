package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.tasks.AnswerRequirements
import javax.inject.Inject

@HiltViewModel
class SubmitAnswerViewModel @Inject constructor() : ViewModel() {
    fun getAnswerRequirements(): AnswerRequirements {
        return AnswerRequirements(300)
    }
}
