package ru.unilms.domain.manage.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.manage.model.User
import ru.unilms.domain.manage.viewmodel.UsersViewModel
import java.util.UUID

@Composable
fun ManageUsersScreen(
    onComposing: (AppBarState, FabState) -> Unit,
    navigate: (Screens, UUID?) -> Unit,
) {

    val viewModel = hiltViewModel<UsersViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var users: List<User>? by remember { mutableStateOf(null) }

    fun updateUsers() = coroutineScope.launch {
        users = viewModel.getUsers()
    }

    LaunchedEffect(true) {
        onComposing(
            AppBarState(
                actions = {}
            ),
            FabState(
                fab = {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                    }
                }
            )
        )
        updateUsers()
    }

    if (users != null) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = users!!,
                itemContent = { user ->
                    ListItem(
                        headlineContent = {
                            Text(text = "${user.lastName} ${user.firstName[0]}. ${if (user.patronymic != null) "${user.patronymic[0]}." else ""}")
                        },
                        supportingContent = {
                            Text(text = stringResource(user.roleName.labelId))
                        },
                        modifier = Modifier.clickable {
                            navigate(Screens.ManageUser, user.id)
                        }
                    )
                }
            )
        }
    }

}