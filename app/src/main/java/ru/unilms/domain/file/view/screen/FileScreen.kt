package ru.unilms.domain.file.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import java.util.UUID

@Composable
fun FileScreen(
    fileId: UUID,
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    LaunchedEffect(true) {
        onComposing(
            AppBarState(
                title = null,
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