package ru.aip.intern.ui.components.calendar.events

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import ru.aip.intern.domain.calendar.data.DeadlineEvent
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.util.UUID

@Composable
fun DeadlineCard(event: DeadlineEvent, navigate: (Screen, UUID) -> Unit) {
    ListItem(
        leadingContent = {
            Icon(
                imageVector = Icons.Outlined.HourglassEmpty,
                contentDescription = null
            )
        },
        headlineContent = { Text(text = event.title) },
        modifier = Modifier.clickable {
            navigate(Screen.Assignment, event.id)
        }
    )
}

@Preview
@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
private fun DeadlineCardPreview() {
    AltenarInternshipTheme {
        DeadlineCard(
            event = DeadlineEvent(
                id = UUID.randomUUID(),
                title = "Test event"
            )
        ) { _, _ -> }
    }
}