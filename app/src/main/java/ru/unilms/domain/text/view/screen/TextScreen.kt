package ru.unilms.domain.text.view.screen

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
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.TextContentInfo
import ru.unilms.domain.text.viewmodel.TextViewModel
import java.util.UUID

@Composable
fun TextScreen(
    textId: UUID,
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {
    val viewModel = hiltViewModel<TextViewModel>()
    var textContent: String by remember { mutableStateOf("") }
    var textContentInfo: TextContentInfo? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    fun updateTextContent() = coroutineScope.launch {
        textContent = viewModel.getTextContent(textId)
        textContentInfo = viewModel.getTextContentInfo(textId)
    }

    LaunchedEffect(key1 = true) {
        updateTextContent()
    }

    LaunchedEffect(textContentInfo) {
        onComposing(
            AppBarState(
                title = textContentInfo?.visibleName,
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
        Material3RichText {
            Markdown(content = textContent)
        }
    }
}