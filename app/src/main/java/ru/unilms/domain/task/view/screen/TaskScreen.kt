package ru.unilms.domain.task.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.DoNotDisturbAlt
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.common.extension.formatAsString
import ru.unilms.domain.task.model.TaskInfo
import ru.unilms.domain.task.util.enums.TaskStatus
import ru.unilms.domain.task.viewmodel.TaskViewModel
import java.util.UUID

@Composable
fun TaskScreen(
    taskId: UUID,
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<TaskViewModel>()
    var taskInfo: TaskInfo? by remember { mutableStateOf(null) }

    fun updateTaskInfo() = coroutineScope.launch {
        taskInfo = viewModel.getTaskInfo(taskId)
    }

    LaunchedEffect(true) {
        updateTaskInfo()
    }

    LaunchedEffect(taskInfo) {
        onComposing(
            AppBarState(
                title = taskInfo?.title,
                actions = { }
            ),
            FabState(
                fab = {}
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ListItem(
            leadingContent = { Icon(Icons.Outlined.CalendarToday, null) },
            headlineContent = { Text(text = stringResource(R.string.label_deadline)) },
            trailingContent = {
                Text(
                    text = taskInfo?.availableUntil?.formatAsString() ?: ""
                )
            }
        )
        ListItem(
            leadingContent = {
                Icon(
                    when (taskInfo?.status) {
                        TaskStatus.NotSent -> Icons.Outlined.DoNotDisturbAlt
                        TaskStatus.Sent -> Icons.Outlined.Cached
                        TaskStatus.Checked -> Icons.Outlined.Checklist
                        TaskStatus.Overdue -> Icons.Outlined.TimerOff
                        else -> Icons.Outlined.QuestionMark
                    }, null
                )
            },
            headlineContent = { Text(text = stringResource(R.string.label_task_status)) },
            trailingContent = {
                Text(
                    text = when (taskInfo?.status) {
                        TaskStatus.NotSent -> stringResource(R.string.task_status_not_sent)
                        TaskStatus.Sent -> stringResource(R.string.task_status_sent)
                        TaskStatus.Checked -> stringResource(R.string.task_status_checked)
                        TaskStatus.Overdue -> stringResource(R.string.task_status_overdue)
                        else -> ""
                    }
                )
            }
        )

        ListItem(
            leadingContent = { Icon(Icons.Outlined.StarRate, null) },
            headlineContent = { Text(text = stringResource(R.string.label_mark)) },
            trailingContent = {
                Text(
                    text = "${taskInfo?.rating} / ${taskInfo?.maximumPoints}"
                )
            }
        )

        if (taskInfo != null && taskInfo!!.solutions.isNotEmpty()) {
            Text(
                text = stringResource(R.string.label_task_solutions),
                style = MaterialTheme.typography.titleMedium
            )

            LazyColumn {
                items(
                    items = taskInfo!!.solutions,
                    itemContent = {
                        ListItem(
                            headlineContent = {
                                Text(text = it.dateTime.formatAsString())
                            },
                            overlineContent = {
                                Text(
                                    text = stringResource(
                                        R.string.label_task_solution_amount_of_files,
                                        it.amountOfFiles
                                    )
                                )
                            }
                        )
                    }
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { navigate(Screens.SubmitAnswer, taskId) }) {
                Text(text = stringResource(R.string.button_send_answer))
            }
        }

    }
}