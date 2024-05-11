package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.aip.intern.R
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.ui.state.QuestionState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuestionScreen(state: QuestionState, onRefresh: () -> Unit) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            Greeting(name = stringResource(R.string.quiz_pass_attempt))
        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}