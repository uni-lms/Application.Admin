package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.InternshipCard
import ru.aip.intern.ui.state.InternshipsState
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternshipsScreen(
    state: InternshipsState,
    onRefresh: () -> Unit,
    goToInternship: (UUID) -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            state.internships.forEach {
                InternshipCard(internship = it) { id ->
                    goToInternship(id)
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

@Preview
@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
fun InternshipsScreenPreview() {
    AltenarInternshipTheme {
        InternshipsScreen(
            state = InternshipsState(),
            onRefresh = { },
            goToInternship = { _ -> }
        )
    }
}