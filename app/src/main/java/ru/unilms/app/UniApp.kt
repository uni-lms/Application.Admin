package ru.unilms.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
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
import ru.unilms.components.global.UniAppTopBar
import ru.unilms.components.global.UniBottomNavigation
import ru.unilms.components.global.UniSideBar
import ru.unilms.screens.ArchiveScreen
import ru.unilms.screens.CalendarScreen
import ru.unilms.screens.CourseScreen
import ru.unilms.screens.CoursesScreen
import ru.unilms.screens.FeedScreen
import ru.unilms.screens.JournalScreen
import ru.unilms.screens.LoginOrSignUpScreen
import ru.unilms.screens.LoginScreen
import ru.unilms.screens.SelectApiUriScreen
import ru.unilms.screens.SignUpScreen
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
        UniAppScreen.Feed.name
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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            UniSideBar(navController, drawerState, viewModel.store)
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                UniAppTopBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null && currentScreen.canGoBack,
                    navigateUp = { navController.navigateUp() })
            },
            bottomBar = {
                if (currentScreen.showBottomAppBar) {
                    UniBottomNavigation(navController, drawerState)
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
                    LoginScreen { goToScreen(navController, UniAppScreen.Feed) }
                }
                composable(UniAppScreen.SignUp.name) {
                    SignUpScreen { goToScreen(navController, UniAppScreen.Feed) }
                }
                composable(UniAppScreen.Feed.name) {
                    FeedScreen()
                }
                composable(UniAppScreen.Courses.name) {
                    CoursesScreen { id ->
                        goToScreenWithId(
                            navController,
                            UniAppScreen.Course,
                            id
                        )
                    }
                }
                composable(UniAppScreen.Calendar.name) {
                    CalendarScreen()
                }
                composable(UniAppScreen.Archive.name) {
                    ArchiveScreen { id ->
                        goToScreenWithId(
                            navController,
                            UniAppScreen.Course,
                            id
                        )
                    }
                }
                composable(UniAppScreen.Journal.name) {
                    JournalScreen()
                }
                composable("${UniAppScreen.Course.name}/{courseId}") {
                    val courseId = backStackEntry?.arguments?.getString("courseId")
                    courseId?.let {
                        CourseScreen(courseId = UUID.fromString(courseId))
                    }
                }
            }
        }
    }
}

fun goToScreenFromNavBar(
    navController: NavHostController,
    screen: UniAppScreen
) {
    navController.navigate(screen.name) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun goToScreen(navController: NavHostController, screen: UniAppScreen) {
    navController.navigate(screen.name)
}

fun goToScreenWithId(navController: NavHostController, screen: UniAppScreen, id: UUID) {
    navController.navigate("${screen.name}/${id}")
}

@Preview
@Composable
fun UniAppPreview() {
    UniApp()
}