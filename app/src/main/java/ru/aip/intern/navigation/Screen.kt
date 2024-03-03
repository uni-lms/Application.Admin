package ru.aip.intern.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(
    val title: String,
    val canGoBack: Boolean = false,
    val icon: ImageVector? = null,
    val position: ScreenPosition
) {
    Internships(
        title = "Стажировки",
        canGoBack = false,
        icon = Icons.Outlined.LibraryBooks,
        position = ScreenPosition.BottomBar,
    ),
    Menu(
        title = "Меню",
        canGoBack = false,
        icon = Icons.Outlined.Menu,
        position = ScreenPosition.BottomBar
    )
}