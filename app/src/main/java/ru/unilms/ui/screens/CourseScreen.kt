package ru.unilms.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.unilms.R
import ru.unilms.app.UniAppScreen
import java.util.UUID

@Composable
fun CourseScreen(courseId: UUID, navigate: (UniAppScreen, UUID) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = courseId.toString())
        ListItem(
            leadingContent = { Icon(Icons.Outlined.Book, null) },
            headlineContent = { Text(text = stringResource(id = R.string.screen_journal)) },
            modifier = Modifier.clickable {
                navigate(UniAppScreen.Journal, courseId)
            }
        )
    }
}