package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.courses.Course
import ru.unilms.utils.enums.CourseType
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CoursesScreenViewModel @Inject constructor() : ViewModel() {

    var isLoading = false

    private val archivedCourses = listOf(
        Course(
            UUID.randomUUID(),
            "Технологии программирования",
            80f,
            3,
            listOf("Вершинин В. В.", "Данилов В. В.")
        ),
        Course(
            UUID.randomUUID(),
            "Алгоритмы и структуры данных",
            20f,
            2,
            listOf("Шамышева О. Н.")
        )
    )

    private val currentCourses = listOf(
        Course(
            UUID.randomUUID(),
            "Графические информационные технологии",
            50f,
            5,
            listOf("Монахова Г. Е.")
        ),
        Course(
            UUID.randomUUID(),
            "Информационные сети",
            25f,
            5,
            listOf("Курочкин С. В.")
        ),
        Course(
            UUID.randomUUID(),
            "Моделирование систем",
            45f,
            5,
            listOf("Шамышева О. Н.")
        ),
        Course(
            UUID.randomUUID(),
            "Распределённые программные системы",
            90f,
            5,
            listOf("Проскурина Г. В.")
        ),
    )

    private val futureCourses = listOf(
        Course(
            UUID.randomUUID(),
            "Информационные технологии в образовании",
            40f,
            7,
            listOf("Озерова М. И.")
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

    fun loadCourses(coursesType: CourseType = CourseType.Current): List<Course> {
        isLoading = true

        val courses = when (coursesType) {
            CourseType.Archived -> archivedCourses
            CourseType.Current -> currentCourses
            CourseType.Future -> futureCourses
        }

        isLoading = false

        return courses
    }

}
