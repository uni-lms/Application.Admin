package ru.unilms.domain.menu.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.auth.util.enums.UserRole
import ru.unilms.domain.menu.viewmodel.MenuViewModel

@Composable
fun MenuScreen(
    navigate: (Screens) -> Unit,
    onComposing: (AppBarState) -> Unit
) {

    val viewModel = hiltViewModel<MenuViewModel>()
    var whoami by remember { mutableStateOf(viewModel.emptyUser) }
    val coroutineScope = rememberCoroutineScope()

    fun updateUser() = coroutineScope.launch {
        whoami = viewModel.whoami()
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = {
                    IconButton(onClick = { navigate(Screens.Settings) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                }
            )
        )
        updateUser()
    }
    Column {
        ListItem(
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding),
            leadingContent = {
                when (whoami.role) {
                    UserRole.Administrator -> Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null
                    )

                    UserRole.Student -> Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = null
                    )

                    UserRole.Tutor -> Icon(
                        imageVector = Icons.Outlined.SupervisorAccount,
                        contentDescription = null
                    )
                }
            },
            headlineContent = { Text(text = whoami.fullName) },
            supportingContent = { Text(text = whoami.email) }
        )

        enumValues<Screens>().forEach { screen ->
            if (screen.showInMenu && screen.icon != null) {
                ListItem(
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .clickable {
                            navigate(screen)
                        },
                    leadingContent = {
                        Icon(
                            screen.icon,
                            null,
                        )
                    },
                    headlineContent = { Text(text = stringResource(id = screen.title)) },
                )
            }
        }

        ListItem(
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
                .clickable {
                    viewModel.logout()
                },
            leadingContent = {
                Icon(
                    Icons.Outlined.Logout,
                    null,
                )
            },
            headlineContent = { Text(text = stringResource(R.string.button_logout)) },
        )

    }
}