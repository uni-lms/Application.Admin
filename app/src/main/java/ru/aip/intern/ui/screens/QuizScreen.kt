package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aip.intern.R
import ru.aip.intern.domain.content.quiz.data.AccruedPoints
import ru.aip.intern.domain.content.quiz.data.QuizInfo
import ru.aip.intern.domain.content.quiz.data.QuizPassAttempt
import ru.aip.intern.domain.content.quiz.data.isFinished
import ru.aip.intern.domain.content.quiz.data.isNotFinished
import ru.aip.intern.domain.content.quiz.data.isTimed
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.dialogs.CommonDialog
import ru.aip.intern.ui.components.dialogs.StartQuizPassAttemptTextProvider
import ru.aip.intern.ui.components.dialogs.StartTimedQuizPassAttemptTextProvider
import ru.aip.intern.ui.state.QuizState
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import ru.aip.intern.util.format
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuizScreen(
    state: QuizState,
    onRefresh: () -> Unit,
    toggleDialog: () -> Unit,
    onContinueAttempt: (UUID) -> Unit,
    onStartAttempt: () -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {

            if (state.quizInfo.description != null) {
                Text(text = state.quizInfo.description)

                Spacer(modifier = Modifier.height(20.dp))

            }

            ListItem(
                leadingContent = {
                    Icon(Icons.Outlined.HourglassTop, null)
                },
                headlineContent = {
                    Text(text = stringResource(R.string.available_until))
                },
                trailingContent = {
                    Text(text = state.quizInfo.availableUntil.format())
                }
            )

            if (state.quizInfo.isTimed()) {
                ListItem(
                    leadingContent = {
                        Icon(Icons.Outlined.Timer, null)
                    },
                    headlineContent = {
                        Text(text = stringResource(R.string.time_limit))
                    },
                    trailingContent = {

                        Text(text = state.quizInfo.timeLimit!!)

                    }
                )
            }

            ListItem(
                leadingContent = {
                    Icon(Icons.Outlined.Replay, null)
                },
                headlineContent = {
                    Text(text = stringResource(R.string.allowed_attempts))
                },
                trailingContent = {
                    Text(text = state.quizInfo.allowedAttempts.toString())
                }
            )

            if (state.quizInfo.attempts.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.quiz_pass_attempts),
                    style = MaterialTheme.typography.titleMedium
                )

                state.quizInfo.attempts.sortedBy { it.startedAt }.forEachIndexed { ind, attempt ->
                    ListItem(
                        overlineContent = {
                            if (attempt.isFinished()) {
                                Text(text = "${attempt.startedAt.format()} — ${attempt.finishedAt!!.format()}")
                            } else {
                                Text(text = "${attempt.startedAt.format()} — …")
                            }
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(
                                    R.string.attempt_title,
                                    ind + 1
                                )
                            )
                        },
                        supportingContent = {
                            if (attempt.isFinished()) {
                                Text(text = "${attempt.accruedPoints.accrued} / ${attempt.accruedPoints.max}")
                            }
                        },
                        trailingContent = {
                            if (attempt.isNotFinished()) {
                                IconButton(onClick = { onContinueAttempt(attempt.id) }) {
                                    Icon(Icons.Outlined.PlayArrow, null)
                                }
                            }
                        }
                    )
                }

            }

            if (state.quizInfo.allowedAttempts > state.quizInfo.attempts.count()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { toggleDialog() }) {
                        Text(text = stringResource(R.string.start_attempt))
                    }
                }
            }

        }

        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )

        if (state.isStartAttemptDialogVisible) {
            CommonDialog(
                dialogText = if (state.quizInfo.isTimed()) {
                    StartTimedQuizPassAttemptTextProvider()
                } else {
                    StartQuizPassAttemptTextProvider()
                },
                onDismiss = toggleDialog,
                onConfirmation = {
                    toggleDialog()
                    onStartAttempt()
                },
                onCancel = toggleDialog
            )
        }
    }

}

@Preview
@Composable
private fun QuizScreenPreview() {
    AltenarInternshipTheme {
        QuizScreen(
            state = QuizState(
                false,
                quizInfo = QuizInfo(
                    id = UUID.randomUUID(),
                    title = "Тест 1",
                    "Lorem ipsum dolor sit amet",
                    availableUntil = LocalDateTime.now().plusDays(2),
                    timeLimit = "40 minutes",
                    allowedAttempts = 5,
                    attempts = listOf(
                        QuizPassAttempt(
                            id = UUID.randomUUID(),
                            startedAt = LocalDateTime.now(),
                            finishedAt = null,
                            accruedPoints = AccruedPoints(
                                accrued = 0,
                                max = 10,
                            )
                        ),
                        QuizPassAttempt(
                            id = UUID.randomUUID(),
                            startedAt = LocalDateTime.now(),
                            finishedAt = LocalDateTime.now().plusMinutes(35),
                            accruedPoints = AccruedPoints(
                                accrued = 9,
                                max = 10,
                            )
                        ),
                        QuizPassAttempt(
                            id = UUID.randomUUID(),
                            startedAt = LocalDateTime.now(),
                            finishedAt = LocalDateTime.now().plusMinutes(25),
                            accruedPoints = AccruedPoints(
                                accrued = 10,
                                max = 10,
                            )
                        )
                    )
                )
            ),
            onRefresh = {},
            toggleDialog = {},
            onContinueAttempt = {},
            onStartAttempt = {},
        )
    }
}