package ru.unilms.ui.screens

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
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText
import kotlinx.coroutines.launch
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import ru.unilms.domain.model.courses.FileContentInfo
import ru.unilms.viewmodels.TextViewModel
import java.util.UUID

@Composable
fun TextScreen(
    textId: UUID,
    navigate: (UniAppScreen, UUID) -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    val viewModel = hiltViewModel<TextViewModel>()
    var fileContent: String by remember { mutableStateOf("") }
    var fileContentInfo: FileContentInfo? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    fun updateTextContent() = coroutineScope.launch {
        fileContent = viewModel.getFileContent(textId)
        fileContentInfo = viewModel.getFileInfo(textId)
    }

    LaunchedEffect(key1 = true) {
        updateTextContent()
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
            .verticalScroll(rememberScrollState())
    ) {
        Material3RichText {
            Markdown(content = fileContent)
        }
    }
}