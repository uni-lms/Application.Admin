package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.quiz.QuizInfo
import javax.inject.Inject

@HiltViewModel
class QuizInfoViewModel @Inject constructor() : ViewModel() {
    val data = QuizInfo("Тест", 10, 1, 59)
}