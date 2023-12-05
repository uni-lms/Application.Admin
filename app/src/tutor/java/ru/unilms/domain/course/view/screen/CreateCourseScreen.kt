package ru.unilms.domain.course.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import ru.unilms.domain.course.Block
import ru.unilms.domain.course.viewmodel.CreateCourseViewModel
import ru.unilms.domain.manage.model.Group
import java.util.UUID

@Composable
fun CreateCourseScreen(
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<CreateCourseViewModel>()

    var blocks: List<Block>? by remember {
        mutableStateOf(null)
    }
    var groups: List<Group>? by remember {
        mutableStateOf(null)
    }

    fun updateBlocks() = coroutineScope.launch {
        blocks = viewModel.getBlocks()
    }

    fun updateGroups() = coroutineScope.launch {
        groups = viewModel.getGroups()
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
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
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

    }
}