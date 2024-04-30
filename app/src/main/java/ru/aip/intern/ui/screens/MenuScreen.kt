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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.navigation.Screen
import ru.aip.intern.navigation.ScreenPosition
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.MenuViewModel
import ru.aip.intern.viewmodels.StartScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuScreen(navigateTo: (Screen) -> Unit) {

    val viewModel: MenuViewModel = hiltViewModel()
    val startScreenViewModel: StartScreenViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refresh() }
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
                        val screenToGo = when (screen) {
                            Screen.Notifications -> Screen.Notifications
                            else -> null
                        }

                        if (screenToGo != null) {
                            navigateTo(screenToGo)
                        }
                    }
                )
            }

            ListItem(
                headlineContent = { Text(text = stringResource(R.string.log_out)) },
                leadingContent = {
                    Icon(Icons.AutoMirrored.Outlined.Logout, null)
                },
                modifier = Modifier.clickable {
                    viewModel.logOut()
                    navigateTo(Screen.Login)
                    startScreenViewModel.updateStartScreen(Screen.Login)
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