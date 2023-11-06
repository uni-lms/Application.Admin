package ru.unilms.domain.file.view.screen

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
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.file.model.FileContentInfo
import ru.unilms.domain.file.viewmodel.FileViewModel
import java.util.UUID

@Composable
fun FileScreen(
    fileId: UUID,
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<FileViewModel>()
    var fileContentInfo: FileContentInfo? by remember { mutableStateOf(null) }

    fun updateFileInfo() = coroutineScope.launch {
        fileContentInfo = viewModel.getFileInfo(fileId)
    }

    LaunchedEffect(true) {
        updateFileInfo()
    }

    LaunchedEffect(fileContentInfo) {
        onComposing(
            AppBarState(
                title = fileContentInfo?.visibleName,
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

    }
}