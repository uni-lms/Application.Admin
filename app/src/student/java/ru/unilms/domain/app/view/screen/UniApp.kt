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
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.app.util.goToScreen
import ru.unilms.domain.app.viewmodel.UniAppViewModel
import ru.unilms.domain.auth.view.screen.LoginOrSignUpScreen
import ru.unilms.domain.auth.view.screen.LoginScreen
import ru.unilms.domain.auth.view.screen.SelectApiUriScreen
import ru.unilms.domain.auth.view.screen.SignUpScreen
import ru.unilms.domain.calendar.view.screen.CalendarScreen
import ru.unilms.domain.common.view.component.navigation.UniAppTopBar
import ru.unilms.domain.common.view.component.navigation.UniBottomNavigation
import ru.unilms.domain.course.view.screen.CourseScreen
import ru.unilms.domain.course.view.screen.CoursesScreen
import ru.unilms.domain.file.view.screen.FileScreen
import ru.unilms.domain.journal.view.screen.JournalScreen
import ru.unilms.domain.menu.view.screen.MenuScreen
import ru.unilms.domain.quiz.view.screen.QuestionScreen
import ru.unilms.domain.quiz.view.screen.QuizInfoScreen
import ru.unilms.domain.settings.view.screen.SettingsScreen
import ru.unilms.domain.task.view.screen.SubmitAnswerScreen
import ru.unilms.domain.task.view.screen.TaskScreen
import ru.unilms.domain.text.view.screen.TextScreen
import java.util.UUID

@Composable
fun UniApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = hiltViewModel<UniAppViewModel>()

    val token = viewModel.token?.collectAsState(initial = "")?.value!!
    val apiUri = viewModel.apiUri?.collectAsState(initial = "")?.value!!

    val startScreen = if (apiUri == "") {
        Screens.SelectApiUri.name
    } else if (token == "") {
        Screens.LoginOrRegister.name
    } else {
        Screens.Courses.name
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
            composable(Screens.Courses.name) {
                CoursesScreen(
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    },
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    }
                )
            }
            composable(Screens.Calendar.name) {
                CalendarScreen(
                    navigate = { screen, id ->
                        goToScreen(navController, screen, id)
                    },
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
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
            composable("${Screens.Journal.name}/{courseId}") {
                val courseId = backStackEntry?.arguments?.getString("courseId")
                courseId?.let {
                    JournalScreen(courseId = UUID.fromString(it)) { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    }
                }
            }
            composable("${Screens.Course.name}/{courseId}") {
                val courseId = backStackEntry?.arguments?.getString("courseId")
                courseId?.let {
                    CourseScreen(
                        courseId = UUID.fromString(it),
                        navigate = { screen, id ->
                            goToScreen(navController, screen, id)
                        },
                    ) { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    }
                }
            }
            composable("${Screens.Task.name}/{taskId}") {
                val taskId = backStackEntry?.arguments?.getString("taskId")
                taskId?.let {
                    TaskScreen(
                        taskId = UUID.fromString(taskId),
                        navigate = { screen, id -> goToScreen(navController, screen, id) },
                        onComposing = { appBar, fab ->
                            appBarState = appBar
                            fabState = fab
                        })
                }
            }
            composable("${Screens.Text.name}/{textId}") {
                val textId = backStackEntry?.arguments?.getString("textId")
                textId?.let {
                    TextScreen(
                        textId = UUID.fromString(textId),
                        navigate = { screen, id -> goToScreen(navController, screen, id) },
                        onComposing = { appBar, fab ->
                            appBarState = appBar
                            fabState = fab
                        })
                }
            }
            composable("${Screens.File.name}/{fileId}") {
                val fileId = backStackEntry?.arguments?.getString("fileId")
                fileId?.let {
                    FileScreen(
                        fileId = UUID.fromString(fileId),
                        navigate = { screen, id -> goToScreen(navController, screen, id) },
                        onComposing = { appBar, fab ->
                            appBarState = appBar
                            fabState = fab
                        })
                }
            }
            composable("${Screens.SubmitAnswer.name}/{taskId}") {
                val taskId = backStackEntry?.arguments?.getString("taskId")
                taskId?.let {
                    SubmitAnswerScreen(
                        taskId = UUID.fromString(taskId),
                        onComposing = { appBarState = it })
                }
            }
            composable(Screens.Settings.name) {
                SettingsScreen(
                    onComposing = { appBar, fab ->
                        appBarState = appBar
                        fabState = fab
                    },
                )
            }
            composable("${Screens.Quiz.name}/{quizId}") {
                val quizId = backStackEntry?.arguments?.getString("quizId")
                quizId?.let {
                    QuizInfoScreen(
                        quizId = UUID.fromString(quizId),
                        onComposing = { appBar, fab ->
                            appBarState = appBar
                            fabState = fab
                        },
                        navigate = { screen, id, qNumber ->
                            goToScreen(
                                navController,
                                screen,
                                id,
                                qNumber
                            )
                        }
                    )
                }
            }
            composable("${Screens.QuizAttempt.name}/{attemptId}/{questionNumber}") {
                val attemptId = backStackEntry?.arguments?.getString("attemptId")
                val questionNumber = backStackEntry?.arguments?.getString("questionNumber")

                if (attemptId != null && questionNumber != null) {
                    QuestionScreen(
                        UUID.fromString(attemptId),
                        questionNumber.toInt(),
                        onComposing = { appBar, fab ->
                            appBarState = appBar
                            fabState = fab
                        },
                        navigate = { screen, id, qNumber, saveState ->
                            goToScreen(
                                navController,
                                screen,
                                id,
                                qNumber,
                                saveState
                            )
                        }
                    )
                }
            }
        }
    }
}