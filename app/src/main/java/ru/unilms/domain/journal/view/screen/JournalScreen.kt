package ru.unilms.domain.journal.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.util.enums.CourseItemType
import ru.unilms.domain.journal.model.JournalDto
import ru.unilms.domain.journal.viewmodel.JournalViewModel
import java.util.UUID

@Composable
fun JournalScreen(
    courseId: UUID,
    onComposing: (AppBarState, FabState) -> Unit,
    navigate: (Screens, UUID) -> Unit,
) {

    val viewModel = hiltViewModel<JournalViewModel>()
    var journal: JournalDto? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    fun updateJournal() = coroutineScope.launch {
        journal = viewModel.getJournal(courseId)
    }

    LaunchedEffect(true) {
        updateJournal()
    }

    LaunchedEffect(journal) {
        onComposing(
            AppBarState(
                actions = { },
                title = journal?.courseName
            ),
            FabState(
                fab = {}
            )
        )
    }

    if (journal != null) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = journal!!.items, itemContent = {
                ListItem(
                    leadingContent = {
                        when (it.type) {
                            CourseItemType.Quiz -> Icon(Icons.Outlined.Quiz, null)
                            CourseItemType.Task -> Icon(Icons.Outlined.CloudUpload, null)
                            else -> null
                        }
                    },
                    headlineContent = {
                        Text(text = it.name)
                    },
                    supportingContent = {
                        Text(text = it.status)
                    },
                    modifier = Modifier.clickable {
                        navigate(
                            when (it.type) {
                                CourseItemType.Quiz -> Screens.Quiz
                                CourseItemType.Task -> Screens.Task
                                else -> Screens.Task
                            }, it.id
                        )
                    }
                )
            })
        }
    }
}