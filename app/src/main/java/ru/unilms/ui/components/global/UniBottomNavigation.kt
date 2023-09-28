package ru.unilms.ui.components.global

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
import ru.unilms.app.UniAppScreen
import ru.unilms.app.goToScreenFromNavBar

@Composable
fun UniBottomNavigation(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar {
        enumValues<UniAppScreen>().forEach { screen ->
            if (screen.icon != null && !screen.showInDrawer && screen.showInBottomBar) {
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
                        goToScreenFromNavBar(navController, screen)
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