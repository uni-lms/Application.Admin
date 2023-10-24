package ru.unilms.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    attemptId: UUID,
    questionNumber: Int,
    onComposing: (AppBarState) -> Unit,
    navigate: (UniAppScreen, UUID, Int) -> Unit
) {

    val amountOfQuestions = 10

    LaunchedEffect(true) {
        onComposing(
            AppBarState(title = "Попытка выполнения теста")
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            (1..amountOfQuestions).forEach {
                FilterChip(
                    onClick = { navigate(UniAppScreen.QuizAttempt, attemptId, it) },
                    selected = it == questionNumber,
                    label = { Text(text = it.toString()) }
                )
            }
        }

        Text(text = "Вопрос №$questionNumber", style = MaterialTheme.typography.titleLarge)
        Text(text = "Тут типа какой-то текст вопроса")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = {
                    navigate(
                        UniAppScreen.QuizAttempt,
                        attemptId,
                        questionNumber - 1
                    )
                },
                enabled = questionNumber > 1
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.ChevronLeft, contentDescription = null)
                    Text(text = "Назад")
                }
            }
            FilledTonalButton(
                onClick = {
                    navigate(
                        UniAppScreen.QuizAttempt,
                        attemptId,
                        questionNumber + 1
                    )
                },
                enabled = questionNumber < amountOfQuestions
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Вперёд")
                    Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
                }
            }
        }

    }
}