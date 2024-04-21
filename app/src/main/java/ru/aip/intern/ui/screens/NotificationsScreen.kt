package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.notifications.NotificationCard
import ru.aip.intern.viewmodels.NotificationsViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(title: MutableState<String>, navigate: (Screen, UUID) -> Unit) {

    title.value = "Уведомления"

    val viewModel = hiltViewModel<NotificationsViewModel>()
    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val data = viewModel.data.observeAsState(viewModel.defaultContent)

    val unreadNotifications = data.value.notifications.filter { !it.isRead }
    val readNotifications = data.value.notifications.filter { it.isRead }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh() }
    )


    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {

            if (data.value.notifications.isNotEmpty()) {
                Column {
                    if (unreadNotifications.isNotEmpty()) {
                        Text(text = "Непрочитанные")

                        unreadNotifications.forEach {
                            NotificationCard(notification = it, navigate)
                        }
                    }

                    if (unreadNotifications.isNotEmpty() && readNotifications.isNotEmpty()) {
                        HorizontalDivider()
                    }

                    if (readNotifications.isNotEmpty()) {
                        readNotifications.forEach {
                            NotificationCard(notification = it, navigate)
                        }
                    }
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