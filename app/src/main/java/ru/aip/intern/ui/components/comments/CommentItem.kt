package ru.aip.intern.ui.components.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Reply
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.aip.intern.domain.content.assignment.data.Comment
import ru.aip.intern.util.format

@Composable
fun CommentItem(comment: Comment, level: Int) {
    val indent = buildString { repeat(level) { append("• ") } }
    var isModalOpened by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(PaddingValues(start = (8 * level).dp))) {
        ListItem(
            modifier = Modifier.clickable {
                isModalOpened = true
            },
            leadingContent = {
                Text(text = indent)
            },
            overlineContent = {
                Text(text = comment.author)
            },
            headlineContent = {
                Text(text = comment.text)
            }
        )
    }


    if (isModalOpened) {
        AlertDialog(
            onDismissRequest = { isModalOpened = false },
            confirmButton = { },
            text = {
                Column {
                    ListItem(
                        headlineContent = { Text(text = "Отправлен") },
                        trailingContent = { Text(text = comment.createdAt.format()) }
                    )
                    ListItem(
                        modifier = Modifier.clickable {
                            isModalOpened = false
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Reply,
                                contentDescription = null
                            )
                        },
                        headlineContent = { Text(text = "Ответить") }
                    )
                }
            },
        )
    }

}