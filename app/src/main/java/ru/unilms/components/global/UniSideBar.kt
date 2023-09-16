package ru.unilms.components.global

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

        enumValues<UniAppScreen>().forEach { screen ->
            if (screen.showInDrawer && screen.icon != null) {
                NavigationDrawerItem(
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    icon = {
                        Icon(
                            screen.icon,
                            null,
                        )
                    },
                    label = { Text(text = stringResource(id = screen.title)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        goToScreen(navController, screen)
                    }
                )
            }
        }
    }
}