package ru.unilms.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Feed
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
    Login(R.string.login),
    SignUp(R.string.register),
    Feed(R.string.feed, false, true, Icons.Outlined.Feed),
    Courses(R.string.courses, false, true, Icons.Outlined.LibraryBooks),
    Calendar(R.string.calendar, false, true, Icons.Outlined.CalendarMonth),
    Menu(R.string.menu, true, false, Icons.Outlined.Menu),
    Archive(R.string.courses_archive, true, true, Icons.Outlined.Archive, true),
    Journal(R.string.journal, true, true, Icons.Outlined.Book, true),
    Course(R.string.course, true, true, null)
}