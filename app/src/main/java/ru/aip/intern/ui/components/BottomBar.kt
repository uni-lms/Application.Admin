package ru.aip.intern.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.aip.intern.navigation.Screen
import ru.aip.intern.util.goToScreen

@Composable
fun BottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar {
        enumValues<Screen>().filter { screen -> screen.icon != null && screen.showInBottomBar }
            .forEach { screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.name } == true

                NavigationBarItem(
                    label = {
                        Text(
                            text = screen.title,
                            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    selected = isSelected,
                    onClick = { goToScreen(navController, screen, saveEntry = false) },
                    icon = {
                        if (screen.icon != null) {
                            Icon(
                                screen.icon,
                                null,
                                tint = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )
            }
    }
}