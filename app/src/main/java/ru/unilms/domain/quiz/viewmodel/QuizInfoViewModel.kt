package ru.unilms.domain.quiz.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.quiz.view.screen.QuizInfo
import javax.inject.Inject

@HiltViewModel
class QuizInfoViewModel @Inject constructor() : ViewModel() {
    val data = QuizInfo(
        "Тест",
        10,
        1,
        1,
        59,
        listOf(),
        false
    )
}