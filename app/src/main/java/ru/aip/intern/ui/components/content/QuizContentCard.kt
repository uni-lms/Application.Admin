package ru.aip.intern.ui.components.content

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.aip.intern.domain.internships.data.QuizContentItem
import java.util.UUID

@Composable
fun QuizContentCard(content: QuizContentItem, navigate: (UUID) -> Unit) {
    ListItem(
        leadingContent = {
            Icon(imageVector = Icons.Outlined.Quiz, contentDescription = null)
        },
        headlineContent = { Text(text = content.title) },
        modifier = Modifier.clickable {
            navigate(content.id)
        }
    )
}