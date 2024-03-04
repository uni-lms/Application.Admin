package ru.aip.intern.ui.screens

import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.navigation.Screen
import ru.aip.intern.navigation.ScreenPosition
import ru.aip.intern.ui.components.BaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(title: MutableState<String>) {
    // TODO real API call to get notifications count
    val unreadNotificationsCount by remember { mutableIntStateOf(1) }
    title.value = "Меню"

    BaseScreen {
        enumValues<Screen>().filter { screen ->
            screen.icon != null && screen.position == ScreenPosition.Menu
        }.forEach { screen ->
            ListItem(
                headlineContent = { Text(text = screen.title) },
                leadingContent = {
                    if (screen.icon != null) {
                        Icon(screen.icon, null)
                    }
                },
                trailingContent = {
                    if (screen == Screen.Notifications && unreadNotificationsCount > 0) {
                        Badge {
                            Text(text = unreadNotificationsCount.toString())
                        }
                    }
                }
            )
        }
    }
}