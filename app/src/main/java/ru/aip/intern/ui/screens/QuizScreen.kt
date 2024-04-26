package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.viewmodels.QuizViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuizScreen(
    title: MutableState<String>,
    quizId: UUID,
    navigate: (Screen, UUID) -> Unit
) {

    title.value = stringResource(R.string.quiz)

    val viewModel: QuizViewModel =
        hiltViewModel<QuizViewModel, QuizViewModel.Factory>(
            creationCallback = { factory -> factory.create(quizId) }
        )
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val quizData by viewModel.data.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refresh(quizId) }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            Greeting(name = stringResource(R.string.quiz))
        }
    }

}