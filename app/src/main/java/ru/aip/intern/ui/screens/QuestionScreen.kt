package ru.aip.intern.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.SportsScore
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.aip.intern.R
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.dialogs.CommonDialog
import ru.aip.intern.ui.components.dialogs.FinishAttemptTextProvider
import ru.aip.intern.ui.components.form.SelectRadioOrCheck
import ru.aip.intern.ui.state.QuestionState
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuestionScreen(
    state: QuestionState,
    onRefresh: () -> Unit,
    onQuestionChange: (Int) -> Unit,
    onSave: (List<UUID>) -> Unit,
    onDialogToggle: () -> Unit,
    onFinishAttempt: () -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    val selectedChoices = state.questionInfo.choices
        .filter { it.isSelected }
        .toMutableStateList()

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                (1..state.questionInfo.amountOfQuestions).forEach { questionNumber ->
                    FilterChip(
                        selected = state.question == questionNumber,
                        onClick = {
                            onSave(selectedChoices.map { it.id })
                            onQuestionChange(questionNumber)
                        },
                        label = {
                            Text(text = questionNumber.toString())
                        }
                    )
                }
            }

            Text(text = state.questionInfo.text, style = MaterialTheme.typography.titleLarge)

            SelectRadioOrCheck(
                selectedChoices = selectedChoices,
                isMultipleChoicesAllowed = state.questionInfo.isMultipleChoicesAllowed,
                choices = state.questionInfo.choices
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilledTonalButton(
                    enabled = state.question > 1,
                    onClick = {
                        onSave(selectedChoices.map { it.id })
                        onQuestionChange(state.question - 1)
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.ChevronLeft, contentDescription = null)
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.previous_question))
                }

                if (state.question == state.questionInfo.amountOfQuestions) {
                    FilledTonalButton(
                        onClick = onDialogToggle
                    ) {
                        Icon(imageVector = Icons.Outlined.SportsScore, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = stringResource(R.string.finish_attempt))
                    }
                } else {
                    FilledTonalButton(
                        enabled = state.question < state.questionInfo.amountOfQuestions,
                        onClick = {
                            onSave(selectedChoices.map { it.id })
                            onQuestionChange(state.question + 1)
                        }
                    ) {
                        Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = stringResource(R.string.next_question))
                    }
                }
            }
        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )

        if (state.isDialogVisible) {
            CommonDialog(
                dialogText = FinishAttemptTextProvider(),
                onDismiss = onDialogToggle,
                onConfirmation = {
                    onDialogToggle()
                    onFinishAttempt()
                },
                onCancel = onDialogToggle
            )
        }
    }
}