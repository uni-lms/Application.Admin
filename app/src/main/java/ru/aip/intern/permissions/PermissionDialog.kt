package ru.aip.intern.permissions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.aip.intern.R
import ru.aip.intern.util.UiText

@Composable
fun PermissionDialog(
    permission: PermissionTextProvider,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
    onCancel: () -> Unit
) {

    AlertDialog(
        icon = {
            Icon(permission.icon, contentDescription = null)
        },
        title = {
            Text(text = permission.title.asString())
        },
        text = {
            Text(text = permission.description.asString())
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.allow))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(stringResource(R.string.decline))
            }
        }
    )

}

interface PermissionTextProvider {
    val icon: ImageVector
    val title: UiText
    val description: UiText
}

class NotificationsPermissionsTextProvider : PermissionTextProvider {
    override val icon: ImageVector
        get() = Icons.AutoMirrored.Outlined.Message
    override val title: UiText
        get() = UiText.StringResource(R.string.sending_notifications)
    override val description: UiText
        get() = UiText.StringResource(R.string.sending_notifications_description)

}