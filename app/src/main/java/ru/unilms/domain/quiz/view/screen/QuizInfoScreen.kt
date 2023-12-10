package ru.unilms.domain.quiz.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.common.unit.minutes
import ru.unilms.domain.quiz.model.QuizInfo
import ru.unilms.domain.quiz.viewmodel.QuizInfoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

@Composable
fun QuizInfoScreen(
    quizId: UUID,
    onComposing: (AppBarState, FabState) -> Unit,
    navigate: (Screens, UUID, Int?) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<QuizInfoViewModel>()
    var quizInfo: QuizInfo? by remember { mutableStateOf(null) }

    fun updateQuizInfo() = coroutineScope.launch {
        quizInfo = viewModel.getQuizInfo(quizId)
    }

    fun startAttempt() = coroutineScope.launch {
        viewModel.startAttempt(quizId) { id ->
            navigate(Screens.QuizAttempt, id, 1)
        }
    }

    fun formatDateTime(dt: LocalDateTime?): String {
        if (dt == null) return "…"
        return dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
    }

    LaunchedEffect(true) {
        updateQuizInfo()
    }

    LaunchedEffect(quizInfo) {
        onComposing(
            AppBarState(
                title = quizInfo?.visibleName
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListItem(
            leadingContent = {
                Icon(imageVector = Icons.Outlined.QuestionMark, contentDescription = null)
            },
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_amount_of_questions)) },
            trailingContent = {
                Text(
                    text = quizInfo?.amountOfQuestions.toString()
                )
            }
        )
        ListItem(
            leadingContent = {
                Icon(imageVector = Icons.Outlined.Repeat, contentDescription = null)
            },
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_amount_of_attempts)) },
            trailingContent = {
                Text(
                    text = quizInfo?.amountOfAttempts.toString()
                )
            }
        )
        ListItem(
            leadingContent = {
                Icon(imageVector = Icons.Outlined.Timer, contentDescription = null)
            },
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_try_time_limit)) },
            trailingContent = {
                Text(
                    text = quizInfo?.timeLimit?.minutes ?: ""
                )
            }
        )
        if (quizInfo != null && quizInfo!!.remainingAttempts > 0) {
            Button(onClick = { startAttempt() }) {
                Text(text = stringResource(id = R.string.label_quiz_begin_attempt))
            }
        }

        if (quizInfo != null && quizInfo!!.attempts.isNotEmpty()) {
            Text(
                text = stringResource(R.string.label_quiz_pass_attempts),
                style = MaterialTheme.typography.titleMedium
            )

            quizInfo!!.attempts.sortedBy {
                it.startedAt
            }.forEachIndexed { ind, it ->
                ListItem(
                    overlineContent = {
                        Text(text = "${it.accruedPoints} / ${quizInfo!!.maximumPoints}")
                    },
                    headlineContent = {
                        Text(text = stringResource(R.string.label_attempt_name, ind + 1))
                    },
                    supportingContent = {
                        Text(text = "${formatDateTime(it.startedAt)} — ${formatDateTime(it.finishedAt)}")
                    },
                    trailingContent = {
                        if (it.finishedAt == null) {
                            FilledTonalIconButton(
                                onClick = {
                                    navigate(Screens.QuizAttempt, it.id, 1)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.PlayCircleOutline,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
