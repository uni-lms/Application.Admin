package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.aip.intern.navigation.Screen
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.ui.components.ConfirmExit
import ru.aip.intern.ui.fragments.BottomBar
import ru.aip.intern.ui.fragments.SplashScreen
import ru.aip.intern.ui.fragments.TopBar
import ru.aip.intern.util.goToScreen
import ru.aip.intern.viewmodels.AipAppViewModel
import ru.aip.intern.viewmodels.AssignmentViewModel
import ru.aip.intern.viewmodels.CreateEventViewModel
import ru.aip.intern.viewmodels.EventViewModel
import ru.aip.intern.viewmodels.FileViewModel
import ru.aip.intern.viewmodels.InternAssessmentViewModel
import ru.aip.intern.viewmodels.InternsAssessmentViewModel
import ru.aip.intern.viewmodels.InternshipViewModel
import ru.aip.intern.viewmodels.InternshipsViewModel
import ru.aip.intern.viewmodels.MenuViewModel
import ru.aip.intern.viewmodels.NotificationsViewModel
import ru.aip.intern.viewmodels.QuizViewModel
import ru.aip.intern.viewmodels.SolutionViewModel
import ru.aip.intern.viewmodels.StartScreenViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AipApp(
    navController: NavHostController = rememberNavController(),
    snackbarMessageHandler: SnackbarMessageHandler
) {

    var title by remember {
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: StartScreenViewModel = hiltViewModel()
    val appViewModel: AipAppViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(appViewModel.title) {
        scope.launch {
            appViewModel.title.collectLatest {
                title = it.asString(context)
            }
        }
    }

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
                    LoginScreen { screen, saveEntry ->
                        goToScreen(
                            navController,
                            screen,
                            saveEntry = saveEntry
                        )
                    }
                }
                composable(Screen.Internships.name) {
                    val internshipsViewModel: InternshipsViewModel = hiltViewModel()
                    val internshipsState by internshipsViewModel.state.collectAsState()
                    InternshipsScreen(
                        state = internshipsState,
                        onRefresh = internshipsViewModel::refresh,
                        goToInternship = { id -> goToScreen(navController, Screen.Internship, id) }
                    )
                }
                composable("${Screen.Internship.name}/{id}") {
                    val internshipId = backStackEntry?.arguments?.getString("id")
                    if (internshipId != null) {
                        val uuid = UUID.fromString(
                            internshipId
                        )
                        val internshipViewModel =
                            hiltViewModel<InternshipViewModel, InternshipViewModel.Factory>(
                                creationCallback = { factory ->
                                    factory.create(
                                        uuid
                                    )
                                }
                            )

                        val internshipState by internshipViewModel.state.collectAsState()

                        InternshipScreen(
                            state = internshipState,
                            onRefresh = { internshipViewModel.refresh(uuid) },
                            onContentItemClick = { screen, id ->
                                goToScreen(
                                    navController,
                                    screen,
                                    id
                                )
                            },
                            onAssessmentClick = {
                                goToScreen(
                                    navController,
                                    Screen.InternsAssessment,
                                    uuid
                                )
                            }
                        )
                    }
                }
                composable(Screen.Menu.name) {
                    val menuViewModel: MenuViewModel = hiltViewModel()
                    val menuState by menuViewModel.state.collectAsState()

                    MenuScreen(
                        state = menuState,
                        onRefresh = menuViewModel::refresh,
                        onNavigateFromMenu = { screen ->
                            val screenToGo = when (screen) {
                                Screen.Notifications -> Screen.Notifications
                                else -> null
                            }

                            if (screenToGo != null) {
                                goToScreen(navController, screenToGo)
                            }
                        },
                        onLogout = {
                            menuViewModel.logOut()
                            goToScreen(navController, Screen.Login)
                            viewModel.updateStartScreen(Screen.Login)
                        },
                        onCheckForUpdatesClick = menuViewModel::checkForUpdates,
                        onSheetHide = menuViewModel::onSheetHide,
                        onDownloadButtonClick = menuViewModel::onDownload
                    )
                }

                composable(Screen.Calendar.name) {
                    CalendarScreen { screen, id -> goToScreen(navController, screen, id) }
                }

                composable(Screen.Notifications.name) {
                    val notificationsViewModel = hiltViewModel<NotificationsViewModel>()
                    val notificationsState by notificationsViewModel.state.collectAsState()
                    NotificationsScreen(
                        state = notificationsState,
                        onRefresh = notificationsViewModel::refresh,
                        onNotificationClick = { screen, id ->
                            goToScreen(navController, screen, id)
                        }
                    )
                }

                composable("${Screen.File.name}/{id}") {
                    val fileId = backStackEntry?.arguments?.getString("id")
                    if (fileId != null) {
                        val uuid = UUID.fromString(fileId)
                        val fileViewModel = hiltViewModel<FileViewModel, FileViewModel.Factory>(
                            creationCallback = { factory -> factory.create(uuid) }
                        )
                        val fileState by fileViewModel.state.collectAsState()
                        FileScreen(
                            state = fileState,
                            onRefresh = fileViewModel::refresh,
                            onDownloadFile = fileViewModel::downloadFile
                        )
                    }
                }

                composable("${Screen.Assignment.name}/{id}") {
                    val assignmentId = backStackEntry?.arguments?.getString("id")
                    if (assignmentId != null) {
                        val uuid = UUID.fromString(assignmentId)
                        val assignmentViewModel =
                            hiltViewModel<AssignmentViewModel, AssignmentViewModel.Factory>(
                                creationCallback = { factory -> factory.create(uuid) }
                            )
                        val assignmentState by assignmentViewModel.state.collectAsState()
                        AssignmentScreen(
                            state = assignmentState,
                            onRefresh = assignmentViewModel::refresh,
                            onFileClick = {
                                goToScreen(
                                    navController,
                                    Screen.File,
                                    assignmentState.assignment.fileId
                                )
                            },
                            onSolutionClick = { id ->
                                goToScreen(
                                    navController,
                                    Screen.Solution,
                                    id
                                )
                            }
                        )
                    }
                }

                composable("${Screen.Solution.name}/{id}") {
                    val solutionId = backStackEntry?.arguments?.getString("id")
                    if (solutionId != null) {
                        val uuid = UUID.fromString(solutionId)
                        val solutionViewModel =
                            hiltViewModel<SolutionViewModel, SolutionViewModel.Factory>(
                                creationCallback = { factory -> factory.create(uuid) }
                            )

                        val solutionState by solutionViewModel.state.collectAsState()
                        SolutionScreen(
                            state = solutionState,
                            onRefresh = solutionViewModel::refresh,
                            onFileClick = { id ->
                                goToScreen(navController, Screen.File, id)
                            },
                            onCommentTextUpdate = solutionViewModel::updateCommentText,
                            onCommentCreate = solutionViewModel::createComment

                        )
                    }
                }

                composable("${Screen.Event.name}/{id}") {
                    val eventId = backStackEntry?.arguments?.getString("id")
                    if (eventId != null) {
                        val uuid = UUID.fromString(eventId)
                        val eventViewModel = hiltViewModel<EventViewModel, EventViewModel.Factory>(
                            creationCallback = { factory -> factory.create(uuid) }
                        )

                        val eventState by eventViewModel.state.collectAsState()
                        EventScreen(
                            state = eventState,
                            onRefresh = eventViewModel::refresh
                        )
                    }
                }

                composable(Screen.CreateEvent.name) {
                    val createEventViewModel: CreateEventViewModel = hiltViewModel()

                    val createEventState by createEventViewModel.state.collectAsState()
                    CreateEventScreen(
                        state = createEventState,
                        onRefresh = createEventViewModel::refresh,
                        onTitleUpdate = createEventViewModel::updateFormStateTitle,
                        onLinkUpdate = createEventViewModel::updateFormStateLink,
                        onInternshipsUpdate = createEventViewModel::updateFormStateSelectedInternships,
                        onUsersUpdate = createEventViewModel::updateFormStateSelectedUsers,
                        onEventTypeUpdate = createEventViewModel::updateFormStateEventType,
                        onEventCreate = { datePickerState,
                                          startTimePickerState,
                                          endTimePickerState,
                                          userZoneId ->
                            createEventViewModel.createEvent(
                                datePickerState,
                                startTimePickerState,
                                endTimePickerState,
                                userZoneId
                            ) { screen, id ->
                                goToScreen(navController, screen, id)
                            }
                        }
                    )
                }

                composable("${Screen.InternsAssessment.name}/{id}") {
                    val internshipId = backStackEntry?.arguments?.getString("id")
                    if (internshipId != null) {
                        val uuid = UUID.fromString(internshipId)
                        val internsAssessmentViewModel: InternsAssessmentViewModel =
                            hiltViewModel<InternsAssessmentViewModel, InternsAssessmentViewModel.Factory>(
                                creationCallback = { factory -> factory.create(uuid) }
                            )

                        val internsAssessmentState by internsAssessmentViewModel.state.collectAsState()
                        InternsAssessmentScreen(
                            state = internsAssessmentState,
                            onRefresh = { internsAssessmentViewModel.refresh(uuid) },
                            onInternClick = { id ->
                                goToScreen(navController, Screen.InternAssessment, id)
                            }
                        )
                    }
                }

                composable("${Screen.InternAssessment.name}/{id}") {
                    val internId = backStackEntry?.arguments?.getString("id")
                    if (internId != null) {
                        val uuid = UUID.fromString(internId)
                        val internAssessmentViewModel: InternAssessmentViewModel =
                            hiltViewModel<InternAssessmentViewModel, InternAssessmentViewModel.Factory>(
                                creationCallback = { factory -> factory.create(uuid) }
                            )
                        val internAssessmentState by internAssessmentViewModel.state.collectAsState()
                        InternAssessmentScreen(
                            state = internAssessmentState,
                            onRefresh = {
                                internAssessmentViewModel.refresh(uuid)
                            },
                            onSaveButtonClick = { criterionId, newScore ->
                                internAssessmentViewModel.updateScore(uuid, criterionId, newScore)
                            }
                        )
                    }
                }

                composable("${Screen.Quiz.name}/{id}") {
                    val quizId = backStackEntry?.arguments?.getString("id")
                    if (quizId != null) {
                        val uuid = UUID.fromString(quizId)
                        val quizViewModel: QuizViewModel =
                            hiltViewModel<QuizViewModel, QuizViewModel.Factory>(
                                creationCallback = { factory -> factory.create(uuid) }
                            )

                        val quizState by quizViewModel.state.collectAsState()
                        QuizScreen(
                            state = quizState,
                            onRefresh = { quizViewModel.refresh(uuid) }
                        )
                    }
                }
            }
        }
    }
}
