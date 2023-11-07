package ru.unilms.domain.quiz.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.common.unit.minutes
import ru.unilms.domain.quiz.model.QuizInfo
import ru.unilms.domain.quiz.viewmodel.QuizInfoViewModel
import java.util.UUID

@Composable
fun QuizInfoScreen(
    quizId: UUID,
    onComposing: (AppBarState) -> Unit,
    navigate: (Screens, UUID, Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<QuizInfoViewModel>()
    var quizInfo: QuizInfo? by remember { mutableStateOf(null) }

    fun updateQuizInfo() = coroutineScope.launch {
        quizInfo = viewModel.getQuizInfo(quizId)
    }

    LaunchedEffect(true) {
        updateQuizInfo()
    }

    LaunchedEffect(quizInfo) {
        onComposing(
            AppBarState(
                title = quizInfo?.visibleName
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_amount_of_questions)) },
            trailingContent = {
                Text(
                    text = quizInfo?.amountOfQuestions.toString()
                )
            }
        )
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_amount_of_attempts)) },
            trailingContent = {
                Text(
                    text = quizInfo?.amountOfAttempts.toString()
                )
            }
        )
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_try_time_limit)) },
            trailingContent = {
                Text(
                    text = quizInfo?.timeLimit?.minutes ?: ""
                )
            }
        )
        /* TODO: Добавить создание попытки решения теста (привязанной к этому тесту) при клике на кнопку */
        Button(onClick = { navigate(Screens.QuizAttempt, UUID.randomUUID(), 1) }) {
            Text(text = stringResource(id = R.string.label_quiz_begin_attempt))
        }
    }
}
