package ru.unilms.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.unilms.components.global.UniAppTopBar
import ru.unilms.screens.FeedScreen
import ru.unilms.screens.LoginOrSignUpScreen
import ru.unilms.screens.LoginScreen
import ru.unilms.screens.SelectApiUriScreen
import ru.unilms.screens.SignUpScreen
import ru.unilms.viewmodels.UniAppViewModel

@Composable
fun UniApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = hiltViewModel<UniAppViewModel>()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = UniAppScreen.valueOf(
        backStackEntry?.destination?.route ?: UniAppScreen.SelectApiUri.name
    )

    val token = viewModel.token?.collectAsState(initial = "")?.value!!
    val apiUri = viewModel.apiUri?.collectAsState(initial = "")?.value!!

    val startScreen = if (apiUri == "") {
        UniAppScreen.SelectApiUri.name
    } else if (token == "") {
        UniAppScreen.LoginOrRegister.name
    } else {
        UniAppScreen.Feed.name
    }

    Scaffold(
        topBar = {
            UniAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            composable(UniAppScreen.SelectApiUri.name) {
                SelectApiUriScreen { goToScreen(navController, UniAppScreen.LoginOrRegister) }
            }
            composable(UniAppScreen.LoginOrRegister.name) {
                LoginOrSignUpScreen(
                    { goToScreen(navController, UniAppScreen.Login) },
                    { goToScreen(navController, UniAppScreen.SignUp) })
            }
            composable(UniAppScreen.Login.name) {
                LoginScreen()
            }
            composable(UniAppScreen.SignUp.name) {
                SignUpScreen()
            }
            composable(UniAppScreen.Feed.name) {
                FeedScreen()
            }
        }
    }
}

private fun goToScreen(navController: NavHostController, screen: UniAppScreen) {
    navController.navigate(screen.name)
}

@Preview
@Composable
fun UniAppPreview() {
    UniApp()
}