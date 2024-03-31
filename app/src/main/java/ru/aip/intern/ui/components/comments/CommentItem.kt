package ru.aip.intern.ui.components.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.aip.intern.domain.content.assignment.data.Comment
import ru.aip.intern.util.format

@Composable
fun CommentItem(comment: Comment, level: Int) {
    val indent = buildString { repeat(level) { append("• ") } }

    Column(modifier = Modifier.padding(PaddingValues(start = (8 * level).dp))) {
        ListItem(
            leadingContent = {
                Text(text = indent)
            },
            overlineContent = {
                Text(text = comment.author)
            },
            headlineContent = {
                Text(text = comment.text)
            },
            supportingContent = {
                Text(
                    text = comment.createdAt.format()
                )
            }
        )
    }
}