package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import ru.unilms.viewmodels.TaskViewModel
import java.util.UUID

@Composable
fun TaskScreen(
    taskId: UUID,
    navigate: (UniAppScreen, UUID) -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    val viewModel = hiltViewModel<TaskViewModel>()
    val taskDecription by remember { mutableStateOf(viewModel.getTaskDescription()) }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = taskDecription.title,
                actions = { }
            )
        )
    }

    Column {
        Text(taskId.toString())
    }
}