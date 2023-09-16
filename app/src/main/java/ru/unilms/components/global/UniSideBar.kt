package ru.unilms.components.global

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.app.UniAppScreen
import ru.unilms.app.goToScreen
import ru.unilms.components.typography.RegularHeadline

@Composable
fun UniSideBar(navController: NavHostController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(10.dp))
        RegularHeadline(
            text = stringResource(R.string.menu),
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        NavigationDrawerItem(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            icon = {
                Icon(
                    Icons.Outlined.Archive,
                    null,
                )
            },
            label = { Text(text = "Архив курсов") },
            selected = currentDestination?.hierarchy?.any { it.route == UniAppScreen.Archive.name } == true,
            onClick = {
                goToScreen(navController, UniAppScreen.Archive)
                scope.launch {
                    drawerState.close()
                }
            }
        )

        NavigationDrawerItem(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            icon = {
                Icon(
                    Icons.Outlined.Book,
                    null,
                )
            },
            label = { Text(text = "Журнал") },
            selected = currentDestination?.hierarchy?.any { it.route == UniAppScreen.Journal.name } == true,
            onClick = {
                goToScreen(navController, UniAppScreen.Journal)
                scope.launch {
                    drawerState.close()
                }
            }
        )
    }
}