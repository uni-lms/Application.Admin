package ru.unilms.domain.course.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.unilms.R
import ru.unilms.domain.app.util.Screens
import java.util.UUID

@Composable
fun SelectCourseMaterialType(
    courseId: UUID,
    navigate: (Screens, UUID?) -> Unit,
) {


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ListItem(
            modifier = Modifier.clickable {
                navigate(Screens.CreateFile, courseId)
            },
            leadingContent = {
                Icon(Icons.Outlined.FileCopy, null)
            },
            headlineContent = {
                Text(stringResource(R.string.screen_file))
            }
        )
    }
}