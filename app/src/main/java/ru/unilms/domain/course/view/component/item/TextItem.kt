package ru.unilms.domain.course.view.component.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.runtime.Composable
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.course.model.CourseItem
import java.util.UUID

@Composable
fun TextItem(item: CourseItem, onClick: (Screens, UUID) -> Unit) {
    BaseItem(
        icon = Icons.Outlined.EditNote,
        name = item.visibleName,
        onClick = { onClick(Screens.Text, item.id) }
    )
}