package ru.unilms.domain.task.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.task.viewmodel.SubmitAnswerViewModel
import java.util.UUID

@Composable
fun SubmitAnswerScreen(
    taskId: UUID,
    onComposing: (AppBarState) -> Unit
) {
    val viewModel = hiltViewModel<SubmitAnswerViewModel>()
    val title = stringResource(R.string.screen_send_task_answer)
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = title,
                actions = { }
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_file_size_limit)) },
            trailingContent = {
                Text(
                    text = stringResource(id = R.string.label_megabytes, 50)
                )
            }
        )
    }
}