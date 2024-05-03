package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.state.InternsAssessmentState
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternsAssessmentScreen(
    state: InternsAssessmentState,
    onRefresh: () -> Unit,
    onInternClick: (UUID) -> Unit
) {




    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            Column {
                state.assessmentsData.internsAssessment.forEach {
                    ListItem(
                        headlineContent = {
                            Text(text = it.internName)
                        },
                        modifier = Modifier.clickable {
                            onInternClick(it.id)
                        }
                    )
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