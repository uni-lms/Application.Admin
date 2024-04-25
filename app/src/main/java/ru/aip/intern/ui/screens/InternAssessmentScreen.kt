package ru.aip.intern.ui.screens

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
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.InternAssessmentViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternAssessmentScreen(
    title: MutableState<String>,
    internId: UUID,
    navigate: (Screen, UUID?) -> Unit
) {
    title.value = stringResource(R.string.interns_assessment)

    val viewModel: InternAssessmentViewModel =
        hiltViewModel<InternAssessmentViewModel, InternAssessmentViewModel.Factory>(
            creationCallback = { factory -> factory.create(internId) }
        )
    val isRefreshing by viewModel.isRefreshing.observeAsState(false)
    val internData by viewModel.internData.observeAsState(viewModel.defaultContent)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refresh(internId) }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            Column {
                internData.assessments.forEach {
                    ListItem(
                        headlineContent = {
                            Text(text = it.title)
                        },
                        supportingContent = {
                            if (it.description != null) {
                                Text(text = it.description)
                            }
                        },
                        trailingContent = {
                            Text(text = if (it.score == null) "—" else it.score.toString())
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