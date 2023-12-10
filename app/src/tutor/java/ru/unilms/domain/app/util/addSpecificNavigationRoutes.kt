package ru.unilms.domain.app.util

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.unilms.domain.calendar.view.screen.CalendarScreen
import ru.unilms.domain.course.view.screen.CourseScreen
import ru.unilms.domain.course.view.screen.CoursesScreen
import ru.unilms.domain.course.view.screen.CreateCourseScreen
import ru.unilms.domain.course.view.screen.CreateFileScreen
import ru.unilms.domain.course.view.screen.SelectCourseMaterialType
import ru.unilms.domain.file.view.screen.FileScreen
import ru.unilms.domain.journal.view.screen.JournalScreen
import ru.unilms.domain.quiz.view.screen.QuestionScreen
import ru.unilms.domain.quiz.view.screen.QuizInfoScreen
import ru.unilms.domain.task.view.screen.SubmitAnswerScreen
import ru.unilms.domain.task.view.screen.TaskScreen
import ru.unilms.domain.text.view.screen.TextScreen
import java.util.UUID


fun NavGraphBuilder.addSpecificNavigationRoutes(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry?,
    appState: AppStateWrapper,
) {
    composable(Screens.Courses.name) {
        CoursesScreen(
            navigate = { screen, id ->
                goToScreen(navController, screen, id)
            },
            onComposing = { appBar, fab ->
                appState.appBarState = appBar
                appState.fabState = fab
            }
        )
    }
    composable(Screens.CreateCourse.name) {
        CreateCourseScreen(
            navigate = { screen, id ->
                goToScreen(navController, screen, id)
            },
            onComposing = { appBarState, fabState ->
                appState.appBarState = appBarState
                appState.fabState = fabState
            }
        )
    }
    composable(Screens.Calendar.name) {
        CalendarScreen(
            navigate = { screen, id ->
                goToScreen(navController, screen, id)
            },
            onComposing = { appBar, fab ->
                appState.appBarState = appBar
                appState.fabState = fab
            }
        )
    }
    composable("${Screens.Journal.name}/{courseId}") {
        val courseId = backStackEntry?.arguments?.getString("courseId")
        courseId?.let {
            JournalScreen(
                courseId = UUID.fromString(it),
                onComposing = { appBar, fab ->
                    appState.appBarState = appBar
                    appState.fabState = fab
                }, navigate = { screen, id ->
                    goToScreen(navController, screen, id)
                })
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
                appState.appBarState = appBar
                appState.fabState = fab
            }
        }
    }
    composable("${Screens.SelectCourseMaterialType.name}/{courseId}") {
        val courseId = backStackEntry?.arguments?.getString("courseId")
        courseId?.let {
            SelectCourseMaterialType(
                courseId = UUID.fromString(courseId),
                navigate = { screen, id -> goToScreen(navController, screen, id) })
        }
    }
    composable("${Screens.CreateFile}/{courseId}") {
        val courseId = backStackEntry?.arguments?.getString("courseId")
        courseId?.let {
            CreateFileScreen(
                courseId = UUID.fromString(courseId),
                navigate = { screen, id -> goToScreen(navController, screen, id) })
        }
    }
    composable("${Screens.Task.name}/{taskId}") {
        val taskId = backStackEntry?.arguments?.getString("taskId")
        taskId?.let {
            TaskScreen(
                taskId = UUID.fromString(taskId),
                navigate = { screen, id -> goToScreen(navController, screen, id) },
                onComposing = { appBar, fab ->
                    appState.appBarState = appBar
                    appState.fabState = fab
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
                    appState.appBarState = appBar
                    appState.fabState = fab
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
                    appState.appBarState = appBar
                    appState.fabState = fab
                })
        }
    }
    composable("${Screens.SubmitAnswer.name}/{taskId}") {
        val taskId = backStackEntry?.arguments?.getString("taskId")
        taskId?.let {
            SubmitAnswerScreen(
                taskId = UUID.fromString(taskId),
                onComposing = { appState.appBarState = it })
        }
    }
    composable("${Screens.Quiz.name}/{quizId}") {
        val quizId = backStackEntry?.arguments?.getString("quizId")
        quizId?.let {
            QuizInfoScreen(
                quizId = UUID.fromString(quizId),
                onComposing = { appBar, fab ->
                    appState.appBarState = appBar
                    appState.fabState = fab
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
                    appState.appBarState = appBar
                    appState.fabState = fab
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