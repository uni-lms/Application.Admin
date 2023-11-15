package ru.unilms.domain.app.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.AppStateWrapper
import ru.unilms.domain.app.util.Flavor
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.app.util.addSpecificNavigationRoutes
import ru.unilms.domain.app.util.getFlavor
import ru.unilms.domain.app.util.goToScreen
import ru.unilms.domain.app.viewmodel.UniAppViewModel
import ru.unilms.domain.auth.view.screen.LoginOrSignUpScreen
import ru.unilms.domain.auth.view.screen.LoginScreen
import ru.unilms.domain.auth.view.screen.SelectApiUriScreen
import ru.unilms.domain.auth.view.screen.SignUpScreen
import ru.unilms.domain.common.view.component.navigation.UniAppTopBar
import ru.unilms.domain.common.view.component.navigation.UniBottomNavigation
import ru.unilms.domain.menu.view.screen.MenuScreen
import ru.unilms.domain.settings.view.screen.SettingsScreen

@Composable
fun UniApp(
    navController: NavHostController = rememberNavController(),
) {
    val viewModel = hiltViewModel<UniAppViewModel>()

    val token = viewModel.token?.collectAsState(initial = "")?.value!!
    val apiUri = viewModel.apiUri?.collectAsState(initial = "")?.value!!

    val startScreen = if (apiUri == "") {
        Screens.SelectApiUri.name
    } else if (token == "") {
        Screens.LoginOrRegister.name
    } else {
        when (getFlavor()) {
            Flavor.Role.Admin -> Screens.Manage.name
            Flavor.Role.Student -> Screens.Courses.name
            Flavor.Role.Tutor -> Screens.Courses.name
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    val screenName = if (route?.contains("/") == true) {
        route.split("/")[0]
    } else {
        route
    }

    val currentScreen = Screens.valueOf(
        screenName ?: startScreen
    )

    var appBarState by remember {
        mutableStateOf(AppBarState())
    }

    var fabState by remember {
        mutableStateOf(FabState())
    }

    Scaffold(
        topBar = {
            UniAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen.canGoBack,
                navigateUp = { navController.navigateUp() },
                actions = appBarState.actions,
                title = appBarState.title
            )
        },
        bottomBar = {
            if (currentScreen.showBottomAppBar) {
                UniBottomNavigation(navController)
            }
        },
        floatingActionButtonPosition = fabState.position,
        floatingActionButton = fabState.fab
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            addSpecificNavigationRoutes(
                navController,
                backStackEntry,
                AppStateWrapper(appBarState, fabState)
            )
            composable(Screens.SelectApiUri.name) {
                SelectApiUriScreen(
                    onComposing = {
                        appBarState = it
                    },
                    navigate = { screen, id ->
                        goToScreen(
                            navController,
                            screen,
                            id
                        )
                    }
                )
            }
            composable(Screens.LoginOrRegister.name) {
                LoginOrSignUpScreen(
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    },
                    navigate = { screen, id -> goToScreen(navController, screen, id) }
                )
            }
            composable(Screens.Login.name) {
                LoginScreen(
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    },
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    }
                )
            }
            composable(Screens.SignUp.name) {
                SignUpScreen(
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    },
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    }
                )
            }
            composable(Screens.Menu.name) {
                MenuScreen(
                    { screen -> goToScreen(navController, screen) },
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    }
                )
            }

            composable(Screens.Settings.name) {
                SettingsScreen(
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    },
                )
            }
        }
    }
}