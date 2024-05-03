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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.aip.intern.R
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.notifications.NotificationCard
import ru.aip.intern.ui.state.NotificationsState
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(
    state: NotificationsState,
    onRefresh: () -> Unit,
    onNotificationClick: (Screen, UUID) -> Unit
) {
    val unreadNotifications = state.notificationsData.notifications.filter { !it.isRead }
    val readNotifications = state.notificationsData.notifications.filter { it.isRead }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )


    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {

            if (state.notificationsData.notifications.isNotEmpty()) {
                Column {
                    if (unreadNotifications.isNotEmpty()) {
                        Text(text = stringResource(R.string.unread))

                        unreadNotifications.forEach {
                            NotificationCard(notification = it, onNotificationClick)
                        }
                    }

                    if (unreadNotifications.isNotEmpty() && readNotifications.isNotEmpty()) {
                        HorizontalDivider()
                    }

                    if (readNotifications.isNotEmpty()) {
                        readNotifications.forEach {
                            NotificationCard(notification = it, onNotificationClick)
                        }
                    }
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