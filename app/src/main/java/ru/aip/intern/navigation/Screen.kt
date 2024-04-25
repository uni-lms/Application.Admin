package ru.aip.intern.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import ru.aip.intern.R
import ru.aip.intern.util.UiText

enum class Screen(
    val title: UiText,
    val canGoBack: Boolean = false,
    val showBottomBar: Boolean = true,
    val icon: ImageVector? = null,
    val position: ScreenPosition = ScreenPosition.None
) {
    Login(
        title = UiText.StringResource(R.string.login),
        canGoBack = false,
        showBottomBar = false,
    ),
    Internships(
        title = UiText.StringResource(R.string.internships),
        canGoBack = false,
        icon = Icons.AutoMirrored.Outlined.LibraryBooks,
        position = ScreenPosition.BottomBar,
    ),
    Internship(
        title = UiText.StringResource(R.string.internship),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None,
    ),
    Calendar(
        title = UiText.StringResource(R.string.calendar),
        canGoBack = false,
        icon = Icons.Outlined.CalendarMonth,
        position = ScreenPosition.BottomBar
    ),
    Menu(
        title = UiText.StringResource(R.string.menu),
        canGoBack = false,
        icon = Icons.Outlined.Menu,
        position = ScreenPosition.BottomBar
    ),
    Notifications(
        title = UiText.StringResource(R.string.notifications),
        canGoBack = true,
        icon = Icons.Outlined.Notifications,
        position = ScreenPosition.Menu
    ),
    File(
        title = UiText.StringResource(R.string.file),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    ),
    Assignment(
        title = UiText.StringResource(R.string.assignment),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    ),
    Solution(
        title = UiText.StringResource(R.string.solution),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    ),
    Event(
        title = UiText.StringResource(R.string.event),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    ),
    CreateEvent(
        title = UiText.StringResource(R.string.create_event),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    ),
    InternsAssessment(
        title = UiText.StringResource(R.string.interns_assessment),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    ),
    InternAssessment(
        title = UiText.StringResource(R.string.interns_assessment),
        canGoBack = true,
        icon = null,
        position = ScreenPosition.None
    )
}