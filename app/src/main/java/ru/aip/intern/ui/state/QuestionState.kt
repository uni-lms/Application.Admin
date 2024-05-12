package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.quiz.data.QuestionInfo

data class QuestionState(
    val isRefreshing: Boolean = false,
    val questionInfo: QuestionInfo = QuestionInfo(),
    val question: Int = 0,
)
