package ru.unilms.domain.settings.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.unilms.data.AppBarState

@Composable
fun SettingsScreen(onComposing: (AppBarState) -> Unit) {

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

    Column {

    }
}