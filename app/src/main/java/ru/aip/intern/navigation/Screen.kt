package ru.aip.intern.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(
    val title: String,
    val canGoBack: Boolean = false,
    val icon: ImageVector? = null,
    val showInBottomBar: Boolean = false
) {
    Internships(
        title = "Стажировки",
        canGoBack = false,
        icon = Icons.Outlined.LibraryBooks,
        showInBottomBar = true
    ),
    Menu(
        title = "Меню",
        canGoBack = false,
        icon = Icons.Outlined.Menu,
        showInBottomBar = true
    )
}