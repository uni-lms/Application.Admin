package ru.unilms.domain.settings.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState

@Composable
fun SettingsScreen(onComposing: (AppBarState, FabState) -> Unit) {

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

    Column {

    }
}