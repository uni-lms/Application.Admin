package ru.aip.intern.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.domain.assessment.data.Assessment
import ru.aip.intern.navigation.Screen
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

    val isExpandedMap = remember {
        List(internData.assessments.size) { index: Int -> index to false }
            .toMutableStateMap()
    }


    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(28.dp)
        ) {
            LazyColumn {
                internData.assessments.onEachIndexed { index, assessment ->
                    section(
                        data = assessment,
                        isExpanded = isExpandedMap[index] ?: false,
                        onHeaderClick = {
                            isExpandedMap[index] = !(isExpandedMap[index] ?: false)
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

fun LazyListScope.section(
    data: Assessment,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit
) {
    item {

        ListItem(
            headlineContent = { Text(data.title) },
            supportingContent = {
                if (data.description != null) {
                    Text(data.description)
                }
            },
            trailingContent = {
                val icon = if (isExpanded) {
                    Icons.Outlined.KeyboardArrowUp
                } else {
                    Icons.Outlined.KeyboardArrowDown
                }

                Icon(imageVector = icon, contentDescription = null)
            },
            modifier = Modifier.clickable { onHeaderClick() }
        )

    }

    if (isExpanded) {
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "hidden text")
            }
        }
    }
}