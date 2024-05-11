package ru.aip.intern.ui.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Quiz
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
fun CommonDialog(
    dialogText: DialogTextProvider,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(dialogText.icon, contentDescription = null)
        },
        title = {
            Text(text = dialogText.title.asString())
        },
        text = {
            Text(text = dialogText.description.asString())
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
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

interface DialogTextProvider {
    val icon: ImageVector
    val title: UiText
    val description: UiText
}

class StartTimedQuizPassAttemptTextProvider : StartQuizPassAttemptTextProvider() {
    override val description: UiText
        get() = UiText.StringResource(R.string.starting_timed_quiz_pass_attempt_description)

}

open class StartQuizPassAttemptTextProvider : DialogTextProvider {
    override val icon: ImageVector
        get() = Icons.Outlined.Quiz
    override val title: UiText
        get() = UiText.StringResource(R.string.starting_quiz_pass_attempt_title)
    override val description: UiText
        get() = UiText.StringResource(R.string.starting_quiz_pass_attempt_description)

}