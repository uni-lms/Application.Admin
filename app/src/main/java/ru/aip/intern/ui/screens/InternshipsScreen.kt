package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.InternshipCard
import ru.aip.intern.viewmodels.InternshipsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternshipsScreen(title: MutableState<String>, snackbarHostState: SnackbarHostState) {

    val viewModel: InternshipsViewModel = hiltViewModel()
    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val internshipData = viewModel.internshipData.observeAsState(emptyList())

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh() }
    )

    LaunchedEffect(key1 = true) {
        viewModel.snackbarMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    title.value = "Стажировки"

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            internshipData.value.forEach {
                InternshipCard(internship = it) {

                }
            }
        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}