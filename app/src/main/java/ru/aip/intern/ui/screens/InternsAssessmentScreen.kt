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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.InternsAssessmentViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternsAssessmentScreen(
    internshipId: UUID,
    navigate: (Screen, UUID?) -> Unit
) {


    val viewModel: InternsAssessmentViewModel =
        hiltViewModel<InternsAssessmentViewModel, InternsAssessmentViewModel.Factory>(
            creationCallback = { factory -> factory.create(internshipId) }
        )

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refresh(internshipId) }
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
                            navigate(Screen.InternAssessment, it.id)
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