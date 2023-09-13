package ru.unilms.components.global

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.unilms.R
import ru.unilms.components.typography.CenteredRegularHeadline

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UniSideBar() {
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
}