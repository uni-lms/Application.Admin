package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.ConfirmExit
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.ui.fragments.BottomBar
import ru.aip.intern.ui.fragments.SplashScreen
import ru.aip.intern.ui.fragments.TopBar

@Composable
fun AipApp(navController: NavHostController = rememberNavController()) {

    var showSplashScreen by remember { mutableStateOf(true) }
    var startScreen by remember { mutableStateOf(Screen.Notifications) } // Default start screen

    // TODO real API call to get notifications count
    val hasUnreadNotifications by remember { mutableStateOf(1 > 0) }

    val title = remember { mutableStateOf("AIP") }

    val snackbarHostState = remember { SnackbarHostState() }

//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val route = backStackEntry?.destination?.route

//    val screenName = if (route?.contains("/") == true) {
//        route.split("/")[0]
//    } else {
//        route
//    }

    ConfirmExit()

    if (showSplashScreen) {
        SplashScreen(onLoadingComplete = { screenName ->
            startScreen = screenName
            showSplashScreen = false

        })
    } else {
        Scaffold(
            topBar = {
                TopBar(
                    canGoBack = navController.previousBackStackEntry != null && startScreen.canGoBack,
                    goUp = { navController.navigateUp() },
                    title = title
                )
            },
            bottomBar = {
                if (startScreen.showBottomBar) {
                    BottomBar(navController = navController, hasUnreadNotifications)
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startScreen.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Screen.Login.name) {
                    LoginScreen(title)
                }
                composable(Screen.Internships.name) {
                    InternshipsScreen(title, snackbarHostState)
                }

                composable(Screen.Menu.name) {
                    MenuScreen(title)
                }

                composable(Screen.Calendar.name) {
                    CalendarScreen(title)
                }

                composable(Screen.Notifications.name) {
                    BaseScreen {
                        Greeting(name = "notifications")
                    }
                }
            }
        }
    }
}
