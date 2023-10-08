package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.courses.CourseBlock
import ru.unilms.domain.model.courses.CourseContent
import ru.unilms.domain.model.courses.CourseItem
import ru.unilms.utils.enums.CourseItemType
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CourseScreenViewModel @Inject constructor() : ViewModel() {

    fun getCourseContent(): CourseContent {
        return CourseContent(
            "ТП (3 семестр)", listOf(
                CourseBlock(
                    "Лекции",
                    listOf(
                        CourseItem(UUID.randomUUID(), "Лекция 1", CourseItemType.File),
                        CourseItem(UUID.randomUUID(), "Лекция 2", CourseItemType.File),
                        CourseItem(UUID.randomUUID(), "Лекция 3", CourseItemType.File)
                    )
                ),
                CourseBlock(
                    "Рейтинг-контроль №1",
                    listOf(
                        CourseItem(UUID.randomUUID(), "Тест", CourseItemType.Quiz),
                    )
                ),
                CourseBlock(
                    "Лабораторные работы",
                    listOf(
                        CourseItem(UUID.randomUUID(), "МУ к Л/Р №1", CourseItemType.File),
                        CourseItem(UUID.randomUUID(), "Л/Р №1", CourseItemType.Task),
                        CourseItem(UUID.randomUUID(), "МУ к Л/Р №2", CourseItemType.File),
                        CourseItem(UUID.randomUUID(), "Л/Р №2", CourseItemType.Task),
                        CourseItem(UUID.randomUUID(), "МУ к Л/Р №3", CourseItemType.File),
                        CourseItem(UUID.randomUUID(), "Л/Р №3", CourseItemType.Task)
                    )
                ),
            )
        )
    }

}