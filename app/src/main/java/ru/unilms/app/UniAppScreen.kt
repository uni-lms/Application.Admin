package ru.unilms.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ru.unilms.R

enum class UniAppScreen(
    val title: Int = R.string.app_name,
    val canGoBack: Boolean = true,
    val showBottomAppBar: Boolean = false,
    val icon: ImageVector? = null,
    val showInDrawer: Boolean = false
) {
    SelectApiUri(R.string.screen_server_select, false),
    LoginOrRegister,
    Login(R.string.screen_login),
    SignUp(R.string.screen_registration),
    Courses(R.string.screen_courses, false, true, Icons.Outlined.LibraryBooks),
    Calendar(R.string.screen_calendar, false, true, Icons.Outlined.CalendarMonth),
    Menu(R.string.screen_menu, false, true, Icons.Outlined.Menu),
    Archive(R.string.screen_courses_archive, true, true, Icons.Outlined.Archive, true),
    Journal(R.string.screen_journal, true, true, Icons.Outlined.Book, true),
    Course(R.string.screen_course, true, true, null)
}