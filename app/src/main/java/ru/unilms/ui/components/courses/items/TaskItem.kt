package ru.unilms.ui.components.courses.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.runtime.Composable
import ru.unilms.app.UniAppScreen
import ru.unilms.domain.model.courses.CourseItem
import java.util.UUID

@Composable
fun TaskItem(item: CourseItem, onClick: (UniAppScreen, UUID) -> Unit) {
    BaseItem(
        icon = Icons.Outlined.CloudUpload,
        name = item.visibleName,
        onClick = { onClick(UniAppScreen.Task, item.id) }
    )
}