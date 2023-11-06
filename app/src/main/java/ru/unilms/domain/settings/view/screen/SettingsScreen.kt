package ru.unilms.domain.settings.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.data.AppBarState
import ru.unilms.domain.settings.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(onComposing: (AppBarState) -> Unit) {

    val viewModel = hiltViewModel<SettingsViewModel>()

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

    Column {
        ListItem(
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
                .clickable {
                    viewModel.clearServerInfo()
                },
            leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.Language,
                    contentDescription = null
                )
            },
            headlineContent = { Text(text = "Сменить сервер") },
        )
    }
}