package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        BaseScreen {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxHeight()
            ) {
                Row {
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

                Text(text = state.questionInfo.text, style = MaterialTheme.typography.titleMedium)

                SelectRadioOrCheck(
                    selectedChoices = selectedChoices,
                    isMultipleChoicesAllowed = state.questionInfo.isMultipleChoicesAllowed,
                    choices = state.questionInfo.choices
                )

            }
        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}