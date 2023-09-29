package ru.unilms.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.unilms.ui.components.global.UniAppTopBar
import ru.unilms.ui.components.global.UniBottomNavigation
import ru.unilms.ui.screens.CalendarScreen
import ru.unilms.ui.screens.CourseScreen
import ru.unilms.ui.screens.CoursesScreen
import ru.unilms.ui.screens.JournalScreen
import ru.unilms.ui.screens.LoginOrSignUpScreen
import ru.unilms.ui.screens.LoginScreen
import ru.unilms.ui.screens.MenuScreen
import ru.unilms.ui.screens.SelectApiUriScreen
import ru.unilms.ui.screens.SignUpScreen
import ru.unilms.viewmodels.UniAppViewModel
import java.util.UUID

@Composable
fun UniApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = hiltViewModel<UniAppViewModel>()

    val token = viewModel.token?.collectAsState(initial = "")?.value!!
    val apiUri = viewModel.apiUri?.collectAsState(initial = "")?.value!!

    val startScreen = if (apiUri == "") {
        UniAppScreen.SelectApiUri.name
    } else if (token == "") {
        UniAppScreen.LoginOrRegister.name
    } else {
        UniAppScreen.Courses.name
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    val screenName = if (route?.contains("/") == true) {
        route.split("/")[0]
    } else {
        route
    }

    val currentScreen = UniAppScreen.valueOf(
        screenName ?: startScreen
    )

    Scaffold(
        topBar = {
            UniAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen.canGoBack,
                navigateUp = { navController.navigateUp() })
        },
        bottomBar = {
            if (currentScreen.showBottomAppBar) {
                UniBottomNavigation(navController)
            }
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                LoginScreen { goToScreen(navController, UniAppScreen.Courses) }
            }
            composable(UniAppScreen.SignUp.name) {
                SignUpScreen { goToScreen(navController, UniAppScreen.Courses) }
            }
            composable(UniAppScreen.Courses.name) {
                CoursesScreen { id ->
                    goToScreen(
                        navController,
                        UniAppScreen.Course,
                        id
                    )
                }
            }
            composable(UniAppScreen.Calendar.name) {
                CalendarScreen()
            }
            composable(UniAppScreen.Menu.name) {
                MenuScreen(
                    { screen -> goToScreen(navController, screen) },
                    dataStore = viewModel.store
                )
            }
            composable("${UniAppScreen.Journal.name}/{courseId}") {
                val courseId = backStackEntry?.arguments?.getString("courseId")
                courseId?.let {
                    JournalScreen(courseId = UUID.fromString(it))
                }
            }
            composable("${UniAppScreen.Course.name}/{courseId}") {
                val courseId = backStackEntry?.arguments?.getString("courseId")
                courseId?.let {
                    CourseScreen(courseId = UUID.fromString(it)) { screen, id ->
                        goToScreen(navController, screen, id)
                    }
                }
            }
        }
    }
}

fun goToScreen(
    navController: NavHostController,
    screen: UniAppScreen,
    id: UUID? = null,
    clearStack: Boolean = false
) {
    val route: String = if (id == null) {
        screen.name
    } else {
        "${screen.name}/$id"
    }
    navController.navigate(route) {
        if (clearStack) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
fun UniAppPreview() {
    UniApp()
}