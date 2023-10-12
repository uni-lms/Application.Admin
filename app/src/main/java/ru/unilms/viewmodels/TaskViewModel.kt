package ru.unilms.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.unilms.domain.model.tasks.TaskDescription
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {
    fun getTaskDescription(): TaskDescription {
        return TaskDescription("Тест 1")
    }
}