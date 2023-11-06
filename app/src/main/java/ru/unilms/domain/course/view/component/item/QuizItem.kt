package ru.unilms.domain.course.view.component.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.runtime.Composable
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.CourseItem
import java.util.UUID

@Composable
fun QuizItem(item: CourseItem, onClick: (Screens, UUID) -> Unit) {
    BaseItem(
        icon = Icons.Outlined.Quiz,
        name = item.visibleName,
        onClick = { onClick(Screens.Quiz, item.id) }
    )
}