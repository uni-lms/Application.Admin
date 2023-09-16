package ru.unilms.components.global

import androidx.compose.material.Icon
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import ru.unilms.app.UniAppScreen
import ru.unilms.app.goToScreenFromNavBar

@Composable
fun UniBottomNavigation(navController: NavHostController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar {
        enumValues<UniAppScreen>().forEach { screen ->
            if (screen.icon != null) {
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
                        if (screen == UniAppScreen.Menu) {
                            scope.launch {
                                drawerState.open()
                            }
                        } else {
                            goToScreenFromNavBar(navController, screen)
                        }
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