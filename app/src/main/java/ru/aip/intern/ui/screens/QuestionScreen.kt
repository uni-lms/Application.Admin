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
import androidx.compose.ui.unit.dp
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.form.SelectRadioOrCheck
import ru.aip.intern.ui.state.QuestionState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuestionScreen(state: QuestionState, onRefresh: () -> Unit, onQuestionChange: (Int) -> Unit) {

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
                (1..state.questionInfo.amountOfQuestions).forEach {
                    FilterChip(
                        selected = state.question == it,
                        onClick = {
                            onQuestionChange(it)
                        },
                        label = {
                            Text(text = it.toString())
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
                FilledTonalButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.ChevronLeft, contentDescription = null)
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = "Назад")
                }

                FilledTonalButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = "Вперёд")
                }
            }
        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}