package ru.unilms.components.global

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.app.UniAppScreen
import ru.unilms.app.goToScreen
import ru.unilms.components.typography.CenteredRegularHeadline

@Composable
fun UniSideBar(navController: NavHostController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
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
                headlineContent = {
                    Text(
                        text = "Архив курсов",
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                modifier = Modifier.clickable(onClick = {
                    goToScreen(navController, UniAppScreen.Archive)
                    scope.launch {
                        drawerState.close()
                    }
                }),
                leadingContent = {
                    Icon(
                        Icons.Outlined.Archive,
                        null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )

            ListItem(
                headlineContent = {
                    Text(
                        text = "Журнал",
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                modifier = Modifier.clickable(onClick = {
                    goToScreen(navController, UniAppScreen.Journal)
                    scope.launch {
                        drawerState.close()
                    }
                }),
                leadingContent = {
                    Icon(
                        Icons.Outlined.Book,
                        null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        }
    }
}