package ru.unilms.ui.screens

import androidx.compose.runtime.Composable
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import java.util.UUID

@Composable
fun TextScreen(
    textId: UUID,
    navigate: (UniAppScreen, UUID) -> Unit,
    onComposing: (AppBarState) -> Unit
) {
}