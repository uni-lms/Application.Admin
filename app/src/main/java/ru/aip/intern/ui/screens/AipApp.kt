package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.ConfirmExit
import ru.aip.intern.ui.fragments.BottomBar
import ru.aip.intern.ui.fragments.TopBar

@Composable
fun AipApp(navController: NavHostController = rememberNavController()) {

    val startScreen = Screen.Internships.name

    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    val screenName = if (route?.contains("/") == true) {
        route.split("/")[0]
    } else {
        route
    }

    val currentScreen = Screen.valueOf(screenName ?: startScreen)

    ConfirmExit()

    Scaffold(
        topBar = {
            TopBar(
                currentScreen = currentScreen,
                canGoBack = navController.previousBackStackEntry != null && currentScreen.canGoBack,
                goUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            if (currentScreen.showBottomBar) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Internships.name) {
                InternshipsScreen()
            }

            composable(Screen.Menu.name) {
                MenuScreen()
            }
        }
    }
}
