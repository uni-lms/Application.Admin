package ru.unilms.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import ru.unilms.ui.components.courses.items.FileItem
import ru.unilms.ui.components.courses.items.QuizItem
import ru.unilms.ui.components.courses.items.TaskItem
import ru.unilms.utils.enums.CourseItemType
import ru.unilms.viewmodels.CourseScreenViewModel
import java.util.UUID

@Composable
fun CourseScreen(
    courseId: UUID,
    navigate: (UniAppScreen, UUID) -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    val viewModel = hiltViewModel<CourseScreenViewModel>()
    val courseContent by remember { mutableStateOf(viewModel.getCourseContent()) }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = courseContent.subjectAbbreviation,
                actions = { }
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = null
                )
            },
            headlineContent = { Text(text = "Комната ВКС для лекций (Google Meet)") }
        )
        ListItem(
            leadingContent = { Icon(Icons.Outlined.Book, null) },
            headlineContent = { Text(text = stringResource(id = R.string.screen_journal)) },
            modifier = Modifier.clickable {
                navigate(UniAppScreen.Journal, courseId)
            }
        )
        Divider()
        courseContent.blocks.forEach { block ->
            Text(
                text = stringResource(block.title.labelId),
                style = MaterialTheme.typography.titleMedium
            )
            block.items.forEach { item ->
                when (item.type) {
                    CourseItemType.File -> FileItem(
                        item = item,
                        onClick = { screen, id -> navigate(screen, id) })

                    CourseItemType.Quiz -> QuizItem(
                        item = item,
                        onClick = { screen, id -> navigate(screen, id) })

                    CourseItemType.Task -> TaskItem(
                        item = item,
                        onClick = { screen, id -> navigate(screen, id) })
                }
            }
        }
    }
}