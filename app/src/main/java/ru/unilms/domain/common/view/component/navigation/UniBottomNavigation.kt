package ru.unilms.domain.common.view.component.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.app.util.goToScreen

@Composable
fun UniBottomNavigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar {
        enumValues<Screens>().forEach { screen ->
            if (screen.icon != null && !screen.showInMenu && screen.showInBottomAppBar) {
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.name } == true
                NavigationBarItem(
                    label = {
                        Text(
                            text = stringResource(screen.title),
                            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        goToScreen(navController, screen)
                    },
                    icon = {
                        Icon(
                            screen.icon,
                            null,
                            tint = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }
    }
}