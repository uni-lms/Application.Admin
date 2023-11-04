package ru.unilms.domain.model.tasks

import ru.unilms.utils.enums.TaskStatus
import java.time.LocalDateTime

data class TaskDescription(
    val title: String,
    val deadline: LocalDateTime,
    val lastChange: LocalDateTime? = null,
    val status: TaskStatus = TaskStatus.NotSent,
    val mark: TaskMark? = null,
    val amountOfComments: Int
)