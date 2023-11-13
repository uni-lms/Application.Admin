package ru.unilms.domain.task.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.DoNotDisturbAlt
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.task.util.enums.TaskStatus
import ru.unilms.domain.task.viewmodel.TaskViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@Composable
fun TaskScreen(
    taskId: UUID,
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {

    val viewModel = hiltViewModel<TaskViewModel>()
    val taskDescription by remember { mutableStateOf(viewModel.getTaskDescription()) }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = taskDescription.title,
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
                    text = taskDescription.deadline.format(
                        DateTimeFormatter.ofPattern(
                            "dd.MM.yyyy HH:mm",
                            Locale.getDefault()
                        )
                    )
                )
            }
        )
        ListItem(
            leadingContent = {
                Icon(
                    when (taskDescription.status) {
                        TaskStatus.NotSent -> Icons.Outlined.DoNotDisturbAlt
                        TaskStatus.Sent -> Icons.Outlined.Cached
                        TaskStatus.Checked -> Icons.Outlined.Checklist
                        TaskStatus.Overdue -> Icons.Outlined.TimerOff
                    }, null
                )
            },
            headlineContent = { Text(text = stringResource(R.string.label_task_status)) },
            trailingContent = {
                Text(
                    text = when (taskDescription.status) {
                        TaskStatus.NotSent -> stringResource(R.string.task_status_not_sent)
                        TaskStatus.Sent -> stringResource(R.string.task_status_sent)
                        TaskStatus.Checked -> stringResource(R.string.task_status_checked)
                        TaskStatus.Overdue -> stringResource(R.string.task_status_overdue)
                    }
                )
            }
        )
        ListItem(
            leadingContent = { Icon(Icons.Outlined.EditCalendar, null) },
            headlineContent = { Text(text = stringResource(R.string.label_last_change)) },
            trailingContent = {
                Text(
                    text = taskDescription.deadline.format(
                        DateTimeFormatter.ofPattern(
                            "dd.MM.yyyy HH:mm",
                            Locale.getDefault()
                        )
                    )
                )
            }
        )
        if (taskDescription.mark != null) {
            val mark = taskDescription.mark!!

            ListItem(
                leadingContent = { Icon(Icons.Outlined.StarRate, null) },
                headlineContent = { Text(text = stringResource(R.string.label_mark)) },
                trailingContent = {
                    Text(
                        text = "${mark.rating} / ${mark.max}"
                    )
                }
            )
        }

        ListItem(
            leadingContent = { Icon(Icons.Outlined.Comment, null) },
            modifier = Modifier.clickable(onClick = { }),
            headlineContent = {
                Text(text = stringResource(id = R.string.label_comments))
            },
            trailingContent = {
                Text(text = taskDescription.amountOfComments.toString())
            }
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { navigate(Screens.SubmitAnswer, taskId) }) {
                Text(text = stringResource(R.string.button_send_answer))
            }
        }

    }
}