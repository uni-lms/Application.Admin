package ru.aip.intern.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ru.aip.intern.navigation.Screen
import java.util.UUID

@Composable
fun SolutionScreen(title: MutableState<String>, id: UUID, navigate: (Screen, UUID) -> Unit) {
}