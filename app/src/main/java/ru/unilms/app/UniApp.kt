package ru.unilms.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.components.global.UniAppTopBar
import ru.unilms.components.typography.CenteredRegularHeadline
import ru.unilms.screens.CalendarScreen
import ru.unilms.screens.FeedScreen
import ru.unilms.screens.LoginOrSignUpScreen
import ru.unilms.screens.LoginScreen
import ru.unilms.screens.SelectApiUriScreen
import ru.unilms.screens.SignUpScreen
import ru.unilms.viewmodels.UniAppViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UniApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = hiltViewModel<UniAppViewModel>()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val currentScreen = UniAppScreen.valueOf(
        backStackEntry?.destination?.route ?: UniAppScreen.SelectApiUri.name
    )

    val token = viewModel.token?.collectAsState(initial = "")?.value!!
    val apiUri = viewModel.apiUri?.collectAsState(initial = "")?.value!!

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val startScreen = if (apiUri == "") {
        UniAppScreen.SelectApiUri.name
    } else if (token == "") {
        UniAppScreen.LoginOrRegister.name
    } else {
        UniAppScreen.Feed.name
    }
    ModalNavigationDrawer(
        drawerContent = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    CenteredRegularHeadline(
                        text = stringResource(R.string.menu),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    ListItem(
                        text = {
                            Text(
                                text = "Архив курсов",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        },
                        icon = {
                            Icon(
                                Icons.Outlined.Archive,
                                null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    )

                    ListItem(
                        text = {
                            Text(
                                text = "Журнал",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        },
                        icon = {
                            Icon(
                                Icons.Outlined.Book,
                                null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    )
                }
            }
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                UniAppTopBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null && currentScreen.canGoBack,
                    navigateUp = { navController.navigateUp() })
            },
            bottomBar = {
                if (currentScreen.showBottomAppBar) {
                    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.secondaryContainer) {
                        enumValues<UniAppScreen>().forEach { screen ->
                            if (screen.icon != null) {
                                val isSelected =
                                    currentDestination?.hierarchy?.any { it.route == screen.name } == true
                                BottomNavigationItem(
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
            }
        ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()

            NavHost(
                navController = navController,
                startDestination = startScreen,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                composable(UniAppScreen.SelectApiUri.name) {
                    SelectApiUriScreen { goToScreen(navController, UniAppScreen.LoginOrRegister) }
                }
                composable(UniAppScreen.LoginOrRegister.name) {
                    LoginOrSignUpScreen(
                        { goToScreen(navController, UniAppScreen.Login) },
                        { goToScreen(navController, UniAppScreen.SignUp) })
                }
                composable(UniAppScreen.Login.name) {
                    LoginScreen { goToScreen(navController, UniAppScreen.Feed) }
                }
                composable(UniAppScreen.SignUp.name) {
                    SignUpScreen { goToScreen(navController, UniAppScreen.Feed) }
                }
                composable(UniAppScreen.Feed.name) {
                    FeedScreen()
                }
                composable(UniAppScreen.Calendar.name) {
                    CalendarScreen()
                }
            }
        }
    }
}

private fun goToScreenFromNavBar(
    navController: NavHostController,
    screen: UniAppScreen
) {
    navController.navigate(screen.name) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun goToScreen(navController: NavHostController, screen: UniAppScreen) {
    navController.navigate(screen.name)
}

@Preview
@Composable
fun UniAppPreview() {
    UniApp()
}