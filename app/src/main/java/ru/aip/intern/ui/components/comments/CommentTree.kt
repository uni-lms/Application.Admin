package ru.aip.intern.ui.components.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ru.aip.intern.domain.content.assignment.data.Comment

@Composable
fun CommentTree(comments: List<Comment>, level: Int = 0) {
    Column {
        comments.forEach {
            CommentItem(it, level)

            if (it.childComments.isNotEmpty()) {
                CommentTree(it.childComments, level + 1)
            }

        }
    }
}