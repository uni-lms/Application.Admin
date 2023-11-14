package ru.unilms.domain.manage.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import java.util.UUID

@Composable
fun ManageScreen(navigate: (Screens, UUID?) -> Unit, onComposing: (AppBarState, FabState) -> Unit) {
    LaunchedEffect(true) {
        onComposing(
            AppBarState(
                actions = {}
            ),
            FabState(
                fab = {}
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = null
                )
            },
            headlineContent = { Text(text = stringResource(R.string.screen_groups)) },
            modifier = Modifier.clickable {
                navigate(Screens.ManageGroups, null)
            }
        )
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null
                )
            },
            headlineContent = { Text(text = stringResource(R.string.screen_users)) },
            modifier = Modifier.clickable {
                navigate(Screens.ManageUsers, null)
            }

        )
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null
                )
            },
            headlineContent = { Text(text = stringResource(R.string.screen_schedule)) },
            modifier = Modifier.clickable {
                navigate(Screens.ManageSchedule, null)
            }
        )
    }
}