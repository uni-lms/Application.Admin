package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.domain.events.data.CustomEventType
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.util.formatDate
import ru.aip.intern.util.formatTime
import ru.aip.intern.viewmodels.EventViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventScreen(
    title: MutableState<String>,
    id: UUID,
) {

    val viewModel = hiltViewModel<EventViewModel, EventViewModel.Factory>(
        creationCallback = { factory -> factory.create(id) }
    )
    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh() }
    )

    val data = viewModel.data.observeAsState(viewModel.defaultData)

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        if (data.value.title.isEmpty()) {
            title.value = "Событие"
        }
    }

    LaunchedEffect(data.value.title) {
        if (data.value.title.isNotEmpty()) {
            title.value = data.value.title
        }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            ListItem(
                leadingContent = {
                    if (data.value.type == CustomEventType.Meeting) {
                        Icon(Icons.Outlined.Call, null)
                    }
                },
                headlineContent = { Text(text = "Тип события") },
                trailingContent = { Text(data.value.type.title) }
            )

            ListItem(
                leadingContent = {
                    Icon(Icons.Outlined.CalendarMonth, null)
                },
                headlineContent = { Text(text = "Дата") },
                trailingContent = {
                    if (data.value.startTimestamp.dayOfMonth == data.value.endTimestamp.dayOfMonth &&
                        data.value.startTimestamp.monthValue == data.value.endTimestamp.monthValue &&
                        data.value.startTimestamp.year == data.value.endTimestamp.year
                    ) {
                        Text(data.value.startTimestamp.formatDate())
                    } else {
                        Text("${data.value.startTimestamp.formatDate()} — ${data.value.endTimestamp.formatDate()}")
                    }
                }
            )

            ListItem(
                leadingContent = {
                    Icon(Icons.Outlined.AccessTime, null)
                },
                headlineContent = {
                    Text(text = "Время")
                },
                trailingContent = {
                    Text(text = "${data.value.startTimestamp.formatTime()} — ${data.value.endTimestamp.formatTime()}")
                }
            )

            if (data.value.link != null) {
                ListItem(
                    headlineContent = {
                        Text(text = "Ссылка")
                    },
                    trailingContent = {
                        Icon(Icons.Outlined.ChevronRight, null)
                    },
                    modifier = Modifier.clickable {
                        uriHandler.openUri(data.value.link!!)
                    }
                )
            }
        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}