package ru.unilms.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.app.UniAppScreen
import ru.unilms.app.goToScreen
import ru.unilms.data.DataStore

@Composable
fun MenuScreen(navController: NavHostController, dataStore: DataStore) {

    val scope = rememberCoroutineScope()
    Column {
        enumValues<UniAppScreen>().forEach { screen ->
            if (screen.showInDrawer && screen.icon != null) {
                ListItem(
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .clickable {
                            goToScreen(navController, screen)
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
                    scope.launch {
                        dataStore.updateToken("")
                    }
                },
            leadingContent = {
                Icon(
                    Icons.Outlined.Logout,
                    null,
                )
            },
            headlineContent = { Text(text = stringResource(R.string.button_clear_token)) },
        )
    }
}