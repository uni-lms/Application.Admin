package ru.unilms.domain.quiz.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.unilms.data.AppBarState
import java.util.UUID

@Composable
fun QuizResultsScreen(attemptId: UUID, onComposing: (AppBarState) -> Unit) {

    LaunchedEffect(true) {
        onComposing(
            AppBarState(
                title = null,
                actions = {}
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

    }
}