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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.domain.assessment.data.InternsComparison
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.InternsAssessmentViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternsAssessmentScreen(
    title: MutableState<String>,
    internshipId: UUID,
    navigate: (Screen, UUID?) -> Unit
) {

    title.value = stringResource(R.string.interns_assessment)

    val viewModel: InternsAssessmentViewModel =
        hiltViewModel<InternsAssessmentViewModel, InternsAssessmentViewModel.Factory>(
            creationCallback = { factory -> factory.create(internshipId) }
        )
    val isRefreshing by viewModel.isRefreshing.observeAsState(false)
    val internsData by viewModel.internsData.observeAsState(InternsComparison(emptyList()))

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refresh(internshipId) }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            Column {
                internsData.internsAssessment.forEach {
                    ListItem(
                        headlineContent = {
                            Text(text = it.internName)
                        },
                        modifier = Modifier.clickable {
                            navigate(Screen.InternAssessment, null)
                        }
                    )
                }
            }
        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}