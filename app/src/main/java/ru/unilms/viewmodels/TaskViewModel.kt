package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.tasks.TaskDescription
import ru.unilms.domain.model.tasks.TaskMark
import ru.unilms.utils.enums.TaskStatus
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {
    fun getTaskDescription(): TaskDescription {
        return TaskDescription(
            "Л/Р №1",
            LocalDateTime.now(),
            LocalDateTime.now(),
            TaskStatus.Overdue,
            TaskMark(10f, 6f),
            0
        )
    }
}