package ru.unilms.ui.components.courses.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.runtime.Composable
import ru.unilms.app.UniAppScreen
import ru.unilms.domain.model.courses.CourseItem
import java.util.UUID

@Composable
fun QuizItem(item: CourseItem, onClick: (UniAppScreen, UUID) -> Unit) {
    BaseItem(
        icon = Icons.Outlined.Quiz,
        name = item.visibleName,
        onClick = { onClick(UniAppScreen.Quiz, item.id) }
    )
}