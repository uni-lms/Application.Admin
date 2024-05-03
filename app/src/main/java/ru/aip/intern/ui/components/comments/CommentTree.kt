package ru.aip.intern.ui.components.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.aip.intern.domain.content.assignment.data.Comment
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.time.LocalDateTime
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

@Preview
@Composable
private fun CommentTreePreview() {
    AltenarInternshipTheme {
        CommentTree(
            comments = listOf(
                Comment(
                    id = UUID.randomUUID(),
                    childComments = emptyList(),
                    text = "Test comment",
                    author = "Test author",
                    createdAt = LocalDateTime.now()
                ), Comment(
                    id = UUID.randomUUID(),
                    childComments = emptyList(),
                    text = "Test comment",
                    author = "Test author",
                    createdAt = LocalDateTime.now()
                )
            )
        ) {

        }
    }
}