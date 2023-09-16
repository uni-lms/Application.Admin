package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.courses.Course
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CoursesScreenViewModel @Inject constructor() : ViewModel() {

    val courses = listOf(
        Course(
            UUID.randomUUID(),
            "Технологии программирования",
            80f,
            3,
            listOf("Вершинин В. В.", "Данилов В. В.")
        ),
        Course(UUID.randomUUID(), "Алгоритмы и структуры данных", 20f, 2, listOf("Шамышева О. Н.")),
        Course(
            UUID.randomUUID(),
            "Информационные технологии в образовании",
            40f,
            7,
            listOf("Озерова М. И.")
        ),
        Course(
            UUID.randomUUID(),
            "Графические информационные технологии",
            50f,
            5,
            listOf("Монахова Г. Е.")
        ),
        Course(UUID.randomUUID(), "Графический и веб-дизайн", 10f, 7, listOf("Шамышев Х. Б.")),
        Course(
            UUID.randomUUID(),
            "Качество программно-информационных систем",
            5f,
            7,
            listOf("Ланская М. К.")
        )
    )

}
