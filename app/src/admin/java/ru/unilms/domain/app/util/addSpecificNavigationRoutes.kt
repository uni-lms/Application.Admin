package ru.unilms.domain.app.util

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.unilms.domain.manage.view.screen.ManageScreen


fun NavGraphBuilder.addSpecificNavigationRoutes(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry?,
    appState: AppStateWrapper,
) {
    composable(Screens.Manage.name) {
        ManageScreen(
            onComposing = { appBar, fab ->
                appState.appBarState = appBar
                appState.fabState = fab
            },
            navigate = { screen, id ->
                goToScreen(navController, screen, id)
            }
        )
    }
}