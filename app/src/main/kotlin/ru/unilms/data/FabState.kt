package ru.unilms.data

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable

data class FabState(
    val fab: @Composable () -> Unit = {},
    val position: FabPosition = FabPosition.End,
)
