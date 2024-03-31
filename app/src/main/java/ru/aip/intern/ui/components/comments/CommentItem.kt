package ru.aip.intern.ui.components.comments

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.aip.intern.domain.content.assignment.data.Comment
import java.time.ZoneId
import java.util.UUID

@Composable
fun CommentItem(comment: Comment, level: Int, onReplyClick: (UUID) -> Unit) {
    val indent = buildString { repeat(level) { append("• ") } }

    Column(modifier = Modifier.padding(PaddingValues(start = (8 * level).dp))) {
        ListItem(
            leadingContent = {
                Text(text = indent)
            },
            overlineContent = {
                Text(
                    text = "${comment.author} • ${
                        DateUtils.getRelativeTimeSpanString(
                            comment.createdAt.atZone(
                                ZoneId.of("UTC")
                            ).toInstant().toEpochMilli()
                        )
                    }"
                )
            },
            headlineContent = {
                Text(text = comment.text)
            },
            supportingContent = {
                IconButton(onClick = { onReplyClick(comment.id) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Comment,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        )
    }

}