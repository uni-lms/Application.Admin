package ru.aip.intern.ui.components.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.aip.intern.domain.internships.data.AssignmentContentItem
import ru.aip.intern.domain.internships.data.BaseContentItem
import ru.aip.intern.domain.internships.data.FileContentItem
import ru.aip.intern.domain.internships.data.LinkContentItem
import ru.aip.intern.domain.internships.data.QuizContentItem
import ru.aip.intern.domain.internships.data.TextContentItem
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.util.UUID

@Composable
fun ContentCard(content: BaseContentItem, navigate: (Screen, UUID) -> Unit) {
    if (content is TextContentItem) {
        TextContentCard(content = content)
    }

    if (content is LinkContentItem) {
        LinkContentCard(content = content)
    }

    if (content is FileContentItem) {
        FileContentCard(content = content) { id ->
            navigate(Screen.File, id)
        }
    }

    if (content is AssignmentContentItem) {
        AssignmentContentCard(content = content) { id ->
            navigate(Screen.Assignment, id)
        }
    }

    if (content is QuizContentItem) {
        QuizContentCard(content = content) { id ->
            navigate(Screen.Quiz, id)
        }
    }

}

private class ContentParameterProvider : PreviewParameterProvider<BaseContentItem> {
    override val values: Sequence<BaseContentItem>
        get() = sequenceOf(
            TextContentItem(
                id = UUID.randomUUID(),
                text = "Lorem ipsum dolor sit amet."
            ),
            LinkContentItem(
                id = UUID.randomUUID(),
                title = "Test link",
                link = "https://google.com"
            ),
            FileContentItem(
                id = UUID.randomUUID(),
                title = "Test file"
            ),
            AssignmentContentItem(
                id = UUID.randomUUID(),
                title = "Test assignment"
            ),
            QuizContentItem(
                id = UUID.randomUUID(),
                title = "Test quiz"
            )
        )

}

@Preview
@PreviewLightDark
@Composable
private fun ContentItemPreview(
    @PreviewParameter(ContentParameterProvider::class) content: BaseContentItem
) {
    AltenarInternshipTheme {
        ContentCard(
            content = content
        ) { _, _ -> }
    }
}