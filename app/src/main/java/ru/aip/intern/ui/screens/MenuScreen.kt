package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.aip.intern.R
import ru.aip.intern.navigation.Screen
import ru.aip.intern.navigation.ScreenPosition
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.state.MenuState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuScreen(
    state: MenuState,
    onRefresh: () -> Unit,
    onNavigateFromMenu: (Screen) -> Unit,
    onLogout: () -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {

            ListItem(
                headlineContent = { Text(text = state.whoAmIData.fullName) },
                supportingContent = { Text(text = state.whoAmIData.email) },
                leadingContent = {
                    Icon(Icons.Outlined.Person, null)
                }
            )

            enumValues<Screen>().filter { screen ->
                screen.icon != null && screen.position == ScreenPosition.Menu
            }.forEach { screen ->
                ListItem(
                    headlineContent = { Text(text = screen.title.asString()) },
                    leadingContent = {
                        if (screen.icon != null) {
                            Icon(screen.icon, null)
                        }
                    },
                    trailingContent = {
                        if (screen == Screen.Notifications && state.notificationsCount > 0) {
                            Badge {
                                Text(text = state.notificationsCount.toString())
                            }
                        }
                    },
                    modifier = Modifier.clickable {
                        onNavigateFromMenu(screen)
                    }
                )
            }

            ListItem(
                headlineContent = { Text(text = stringResource(R.string.log_out)) },
                leadingContent = {
                    Icon(Icons.AutoMirrored.Outlined.Logout, null)
                },
                modifier = Modifier.clickable {
                    onLogout()
                }
            )

        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}