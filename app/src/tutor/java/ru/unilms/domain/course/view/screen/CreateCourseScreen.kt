package ru.unilms.domain.course.view.screen

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
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import java.util.UUID

@Composable
fun CreateCourseScreen(
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {

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