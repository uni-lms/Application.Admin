package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.domain.internships.data.Content
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.viewmodels.InternshipViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternshipScreen(
    title: MutableState<String>,
    internshipId: UUID,
    goToScreen: (Screen, UUID) -> Unit
) {

    title.value = "Стажировка"

    val viewModel = hiltViewModel<InternshipViewModel, InternshipViewModel.Factory>(
        creationCallback = { factory -> factory.create(internshipId) }
    )
    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val internshipData =
        viewModel.internshipData.observeAsState(Content(title = "", sections = emptyList()))

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh(internshipId) }
    )

    LaunchedEffect(internshipData) {
        title.value = internshipData.value.title
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            Greeting(name = "Стажировка")
        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}