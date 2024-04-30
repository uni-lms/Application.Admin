package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.domain.events.data.CustomEventType
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.util.formatDate
import ru.aip.intern.util.formatTime
import ru.aip.intern.viewmodels.EventViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventScreen(
    id: UUID,
) {

    val viewModel = hiltViewModel<EventViewModel, EventViewModel.Factory>(
        creationCallback = { factory -> factory.create(id) }
    )

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refresh() }
    )

    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            ListItem(
                leadingContent = {
                    val icon = when (state.eventData.type) {
                        CustomEventType.Meeting -> Icons.Outlined.Call
                    }

                    Icon(icon, null)
                },
                headlineContent = { Text(text = stringResource(R.string.event_type)) },
                trailingContent = { Text(state.eventData.type.title.asString()) }
            )

            ListItem(
                leadingContent = {
                    Icon(Icons.Outlined.CalendarMonth, null)
                },
                headlineContent = { Text(stringResource(R.string.event_date)) },
                trailingContent = {
                    if (state.eventData.startTimestamp.dayOfMonth == state.eventData.endTimestamp.dayOfMonth &&
                        state.eventData.startTimestamp.monthValue == state.eventData.endTimestamp.monthValue &&
                        state.eventData.startTimestamp.year == state.eventData.endTimestamp.year
                    ) {
                        Text(state.eventData.startTimestamp.formatDate())
                    } else {
                        Text("${state.eventData.startTimestamp.formatDate()} — ${state.eventData.endTimestamp.formatDate()}")
                    }
                }
            )

            ListItem(
                leadingContent = {
                    Icon(Icons.Outlined.AccessTime, null)
                },
                headlineContent = {
                    Text(text = stringResource(R.string.time))
                },
                trailingContent = {
                    Text(text = "${state.eventData.startTimestamp.formatTime()} — ${state.eventData.endTimestamp.formatTime()}")
                }
            )

            if (state.eventData.link?.isNotBlank() == true) {
                ListItem(
                    leadingContent = {
                        Icon(Icons.Outlined.Link, null)
                    },
                    headlineContent = {
                        Text(text = stringResource(R.string.link))
                    },
                    trailingContent = {
                        Icon(Icons.Outlined.ChevronRight, null)
                    },
                    modifier = Modifier.clickable {
                        uriHandler.openUri(state.eventData.link!!)
                    }
                )
            }
        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}