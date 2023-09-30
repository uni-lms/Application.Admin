package ru.unilms.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.unilms.data.AppBarState

@Composable
fun CalendarScreen(onComposing: (AppBarState) -> Unit) {

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

}