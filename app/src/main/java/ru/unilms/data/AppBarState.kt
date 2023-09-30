package ru.unilms.data

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppBarState(
    val actions: @Composable RowScope.() -> Unit = {}
)
