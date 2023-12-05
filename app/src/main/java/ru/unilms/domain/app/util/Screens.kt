package ru.unilms.domain.app.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ru.unilms.R

enum class Screens(
    val title: Int = R.string.app_name,
    val canGoBack: Boolean = true,
    val showBottomAppBar: Boolean = false,
    val icon: ImageVector? = null,
    val showInBottomAppBar: Boolean = true,
    val showInMenu: Boolean = false,
    val showOnFlavors: List<Flavor.Role> = listOf(),
) {
    SelectApiUri(R.string.screen_server_select, false, showInBottomAppBar = false),
    LoginOrRegister(showInBottomAppBar = false),
    Login(R.string.screen_login, showInBottomAppBar = false),
    SignUp(R.string.screen_registration, showInBottomAppBar = false),
    Courses(
        R.string.screen_courses,
        false,
        true,
        Icons.Outlined.LibraryBooks,
        true,
        false,
        listOf(Flavor.Role.Student, Flavor.Role.Tutor)
    ),
    Calendar(
        R.string.screen_calendar,
        false,
        true,
        Icons.Outlined.CalendarMonth,
        true,
        false,
        listOf(Flavor.Role.Student, Flavor.Role.Tutor)
    ),
    Manage(
        R.string.screen_manage,
        false,
        true,
        Icons.Outlined.ManageAccounts,
        true,
        false,
        listOf(Flavor.Role.Admin)
    ),
    Menu(
        R.string.screen_menu,
        false,
        true,
        Icons.Outlined.Menu,
        true,
        false,
        listOf(Flavor.Role.Student, Flavor.Role.Tutor, Flavor.Role.Admin)
    ),
    ManageGroups(R.string.screen_groups, true, true, null, false),
    ManageGroup(R.string.screen_group, true, true, null, false),
    ManageUsers(R.string.screen_users, true, true, null, false),
    ManageUser(R.string.screen_manage_user, true, true, null, false),
    CreateCourse(R.string.screen_create_course, true, true, null, false),
    CreateGroup(R.string.screen_create_group, true, true, null, false),
    Journal(R.string.screen_journal, true, true, Icons.Outlined.Book, false),
    Course(R.string.screen_course, true, true, null, false),
    Task(R.string.screen_task, true, true, null, false),
    SubmitAnswer(R.string.screen_send_task_answer, true, true, null, false),
    Quiz(R.string.screen_quiz, true, true, null, false),
    File(R.string.screen_file, true, true, null, false),
    Text(R.string.screen_text, true, true, null, false),
    Lesson(R.string.screen_lesson, true, true, null, false),
    Settings(R.string.screen_settings, true, true, Icons.Filled.Settings, false),
    QuizAttempt(R.string.label_quiz_attempt, false, false, null, false),
}