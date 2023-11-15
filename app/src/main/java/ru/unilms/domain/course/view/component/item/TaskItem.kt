package ru.unilms.domain.course.view.component.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.runtime.Composable
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.CourseItem
import java.util.UUID

@Composable
fun TaskItem(item: CourseItem, onClick: (Screens, UUID) -> Unit) {
    BaseItem(
        icon = Icons.Outlined.CloudUpload,
        name = item.visibleName,
        onClick = { onClick(Screens.Task, item.id) }
    )
}