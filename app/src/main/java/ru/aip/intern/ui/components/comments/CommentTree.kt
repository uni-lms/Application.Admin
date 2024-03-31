package ru.aip.intern.ui.components.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ru.aip.intern.domain.content.assignment.data.Comment
import java.util.UUID

@Composable
fun CommentTree(comments: List<Comment>, level: Int = 0, onReplyClick: (UUID) -> Unit) {
    Column {
        comments.forEach {
            CommentItem(it, level, onReplyClick)

            if (it.childComments.isNotEmpty()) {
                CommentTree(it.childComments, level + 1, onReplyClick)
            }

        }
    }
}