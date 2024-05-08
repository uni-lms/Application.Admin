package ru.aip.intern.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.aip.intern.R
import ru.aip.intern.domain.internal.data.ReleaseInfo
import ru.aip.intern.downloading.installApplication
import ru.aip.intern.navigation.Screen
import ru.aip.intern.navigation.ScreenPosition
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.DownloadButton
import ru.aip.intern.ui.state.MenuState

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    state: MenuState,
    onRefresh: () -> Unit,
    onNavigateFromMenu: (Screen) -> Unit,
    onLogout: () -> Unit,
    onCheckForUpdatesClick: (() -> Unit) -> Unit,
    onSheetHide: (() -> Unit) -> Unit,
    onDownloadButtonClick: (ReleaseInfo) -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                leadingContent = {
                    Icon(Icons.Outlined.SystemUpdate, null)
                },
                headlineContent = {
                    Text(text = stringResource(R.string.check_for_updates))
                },
                modifier = Modifier.clickable {
                    onCheckForUpdatesClick { scope.launch { sheetState.show() } }
                }
            )

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

    if (state.isShowSheet) {
        ModalBottomSheet(
            onDismissRequest = { onSheetHide { scope.launch { sheetState.show() } } },
            sheetState = sheetState
        ) {
            Column(Modifier.padding(20.dp)) {
                if (state.releaseInfo != null) {
                    Text(
                        text = state.releaseInfo.title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                }

                DownloadButton(
                    state = state.downloadButtonState,
                    downloadTitle = stringResource(R.string.download_update),
                    openTitle = stringResource(R.string.install_update),
                    onDownloadStart = {
                        onDownloadButtonClick(state.releaseInfo!!)
                    },
                    onOpenFile = { file ->
                        scope.launch {
                            file.installApplication(context)
                        }
                    }
                )
            }
        }
    }
}