package ru.unilms.app

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.unilms.data.AppBarState
import ru.unilms.ui.components.global.UniAppTopBar
import ru.unilms.ui.components.global.UniBottomNavigation
import ru.unilms.ui.screens.CalendarScreen
import ru.unilms.ui.screens.CourseScreen
import ru.unilms.ui.screens.CoursesScreen
import ru.unilms.ui.screens.JournalScreen
import ru.unilms.ui.screens.LoginOrSignUpScreen
import ru.unilms.ui.screens.LoginScreen
import ru.unilms.ui.screens.MenuScreen
import ru.unilms.ui.screens.QuizInfoScreen
import ru.unilms.ui.screens.SelectApiUriScreen
import ru.unilms.ui.screens.SettingsScreen
import ru.unilms.ui.screens.SignUpScreen
import ru.unilms.ui.screens.SubmitAnswerScreen
import ru.unilms.ui.screens.TaskScreen
import ru.unilms.ui.screens.UnderConstructionScreen
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

    var appBarState by remember {
        mutableStateOf(AppBarState())
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(UniAppScreen.SelectApiUri.name) {
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
            composable(UniAppScreen.LoginOrRegister.name) {
                LoginOrSignUpScreen(
                    onComposing = {
                        appBarState = it
                    },
                    navigate = { screen, id -> goToScreen(navController, screen, id) }
                )
            }
            composable(UniAppScreen.Login.name) {
                LoginScreen(
                    onComposing = {
                        appBarState = it
                    },
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    }
                )
            }
            composable(UniAppScreen.SignUp.name) {
                SignUpScreen(
                    onComposing = {
                        appBarState = it
                    },
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    }
                )
            }
            composable(UniAppScreen.Courses.name) {
                CoursesScreen(
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    },
                    onComposing = {
                        appBarState = it
                    }
                )
            }
            composable(UniAppScreen.Calendar.name) {
                CalendarScreen(
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    },
                    onComposing = {
                        appBarState = it
                    }
                )
            }
            composable(UniAppScreen.Menu.name) {
                MenuScreen(
                    { screen -> goToScreen(navController, screen) },
                    onComposing = {
                        appBarState = it
                    }
                )
            }
            composable("${UniAppScreen.Journal.name}/{courseId}") {
                val courseId = backStackEntry?.arguments?.getString("courseId")
                courseId?.let {
                    JournalScreen(courseId = UUID.fromString(it)) {
                        appBarState = it
                    }
                }
            }
            composable("${UniAppScreen.Course.name}/{courseId}") {
                val courseId = backStackEntry?.arguments?.getString("courseId")
                courseId?.let {
                    CourseScreen(
                        courseId = UUID.fromString(it),
                        navigate = { screen, id ->
                            goToScreen(navController, screen, id)
                        },
                    ) {
                        appBarState = it
                    }
                }
            }
            composable("${UniAppScreen.Task.name}/{taskId}") {
                val taskId = backStackEntry?.arguments?.getString("taskId")
                taskId?.let {
                    TaskScreen(
                        taskId = UUID.fromString(it),
                        navigate = { screen, id -> goToScreen(navController, screen, id) },
                        onComposing = { appBarState = it })
                }
            }
            composable("${UniAppScreen.SubmitAnswer.name}/{taskId}") {
                val taskId = backStackEntry?.arguments?.getString("taskId")
                taskId?.let {
                    SubmitAnswerScreen(
                        taskId = UUID.fromString(taskId),
                        onComposing = { appBarState = it })
                }
            }
            composable(UniAppScreen.Settings.name) {
                SettingsScreen(
                    onComposing = {
                        appBarState = it
                    },
                )
            }
            composable("${UniAppScreen.Quiz.name}/{quizId}") {
                val quizId = backStackEntry?.arguments?.getString("quizId")
                quizId?.let {
                    QuizInfoScreen(
                        quizId = UUID.fromString(quizId),
                    ) {
                        appBarState = it
                    }
                }
            }
            composable(UniAppScreen.UnderConstruction.name) {
                UnderConstructionScreen()
            }
            composable("${UniAppScreen.UnderConstruction.name}/{itemId}") {
                UnderConstructionScreen()
            }
        }
    }
}

fun goToScreen(
    navController: NavHostController,
    screen: UniAppScreen,
    id: UUID? = null,
) {
    val route: String = if (id == null) {
        screen.name
    } else {
        "${screen.name}/$id"
    }
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
fun UniAppPreview() {
    UniApp()
}