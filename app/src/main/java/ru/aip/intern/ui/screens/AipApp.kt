package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.aip.intern.navigation.Screen
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.components.ConfirmExit
import ru.aip.intern.ui.fragments.BottomBar
import ru.aip.intern.ui.fragments.SplashScreen
import ru.aip.intern.ui.fragments.TopBar
import ru.aip.intern.util.goToScreen
import ru.aip.intern.viewmodels.StartScreenViewModel
import java.util.UUID

@Composable
fun AipApp(
    navController: NavHostController = rememberNavController(),
    snackbarMessageHandler: SnackbarMessageHandler
) {

    val title = remember { mutableStateOf("AIP") }

    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: StartScreenViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val route = backStackEntry?.destination?.route

    val screenName = if (route?.contains("/") == true) {
        route.split("/")[0]
    } else {
        route
    }

    val currentScreen = Screen.valueOf(screenName ?: state.startScreen.name)


    LaunchedEffect(snackbarMessageHandler) {
        snackbarMessageHandler.message.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    ConfirmExit()

    if (state.showSplashScreen) {
        SplashScreen()
    } else {
        Scaffold(
            topBar = {
                TopBar(
                    canGoBack = navController.previousBackStackEntry != null && currentScreen.canGoBack,
                    goUp = { navController.navigateUp() },
                    title = title
                )
            },
            bottomBar = {
                if (currentScreen.showBottomBar) {
                    BottomBar(navController = navController)
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = state.startScreen.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Screen.Login.name) {
                    LoginScreen(title) { screen ->
                        goToScreen(
                            navController,
                            screen
                        )
                    }
                }
                composable(Screen.Internships.name) {
                    InternshipsScreen(title) { screen, id ->
                        goToScreen(
                            navController,
                            screen,
                            id
                        )
                    }
                }
                composable("${Screen.Internship.name}/{id}") {
                    val internshipId = backStackEntry?.arguments?.getString("id")
                    if (internshipId != null) {
                        InternshipScreen(
                            title,
                            UUID.fromString(internshipId),
                        ) { screen, id ->
                            goToScreen(
                                navController,
                                screen,
                                id
                            )
                        }
                    }
                }
                composable(Screen.Menu.name) {
                    MenuScreen(title) { screen -> goToScreen(navController, screen) }
                }

                composable(Screen.Calendar.name) {
                    CalendarScreen(title) { screen, id -> goToScreen(navController, screen, id) }
                }

                composable(Screen.Notifications.name) {
                    NotificationsScreen(title) { screen, id ->
                        goToScreen(
                            navController,
                            screen,
                            id
                        )
                    }
                }

                composable("${Screen.File.name}/{id}") {
                    val fileId = backStackEntry?.arguments?.getString("id")
                    if (fileId != null) {
                        FileScreen(title, UUID.fromString(fileId))
                    }
                }

                composable("${Screen.Assignment.name}/{id}") {
                    val assignmentId = backStackEntry?.arguments?.getString("id")
                    if (assignmentId != null) {
                        AssignmentScreen(title, UUID.fromString(assignmentId)) { screen, id ->
                            goToScreen(navController, screen, id)
                        }
                    }
                }

                composable("${Screen.Solution.name}/{id}") {
                    val solutionId = backStackEntry?.arguments?.getString("id")
                    if (solutionId != null) {
                        SolutionScreen(title, UUID.fromString(solutionId)) { screen, id ->
                            goToScreen(navController, screen, id)
                        }
                    }
                }

                composable("${Screen.Event.name}/{id}") {
                    val eventId = backStackEntry?.arguments?.getString("id")
                    if (eventId != null) {
                        EventScreen(title, UUID.fromString(eventId))
                    }
                }

                composable(Screen.CreateEvent.name) {
                    CreateEventScreen(title) { screen, id ->
                        goToScreen(navController, screen, id)
                    }
                }

                composable("${Screen.InternsAssessment.name}/{id}") {
                    val internshipId = backStackEntry?.arguments?.getString("id")
                    if (internshipId != null) {
                        InternsAssessmentScreen(
                            title,
                            UUID.fromString(internshipId)
                        ) { screen, id ->
                            goToScreen(navController, screen, id)
                        }
                    }
                }

                composable("${Screen.InternAssessment.name}/{id}") {
                    val internId = backStackEntry?.arguments?.getString("id")
                    if (internId != null) {
                        InternAssessmentScreen(
                            title,
                            UUID.fromString(internId)
                        )
                    }
                }

                composable("${Screen.Quiz.name}/{id}") {
                    val quizId = backStackEntry?.arguments?.getString("id")
                    if (quizId != null) {
                        QuizScreen(
                            title,
                            UUID.fromString(quizId)
                        ) { screen, id ->
                            goToScreen(navController, screen, id)
                        }
                    }
                }
            }
        }
    }
}
