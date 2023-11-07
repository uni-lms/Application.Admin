package ru.unilms.domain.quiz.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.quiz.model.QuestionChoice
import ru.unilms.domain.quiz.model.QuestionInfo
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {
    fun getQuestionInfo(questionNumber: Int): QuestionInfo {
        return when (questionNumber) {
            1 -> QuestionInfo(
                10,
                "Рейтинг-контроль №1",
                "Что такое полиморфизм?",
                false,
                listOf(
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Возможность реализации наследуемых свойств или методов отличающимися способами в рамках множества абстракций"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Моделирование требуемых атрибутов и взаимодействий сущностей в виде классов для определения абстрактного представления системы"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Скрытие внутреннего состояния и функций объекта и предоставление доступа только через открытый набор функций"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Возможность создания новых абстракций на основе существующих"
                    ),
                )
            )

            else -> QuestionInfo(
                10,
                "Рейтинг-контроль №1",
                "В программной системе SPSS количественно оценить отличие полученного в результате частотного анализа распределения от нормального можно с помощью",
                true,
                listOf(
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Стандартного отклонения"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Коэффициента пикообразности"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Моды"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Медианы"
                    ),
                    QuestionChoice(
                        UUID.randomUUID(),
                        "Коэффициента асимметрии"
                    ),
                )
            )
        }
    }
}