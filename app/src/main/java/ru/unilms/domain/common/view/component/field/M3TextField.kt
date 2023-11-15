package ru.unilms.domain.common.view.component.field

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ru.unilms.domain.common.form.Field

class M3TextField(
    label: String,
    form: Form,
    modifier: Modifier? = Modifier,
    fieldState: FieldState<String?>,
    isVisible: Boolean = true,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    formatter: ((raw: String?) -> String)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    visualTransformation: VisualTransformation = VisualTransformation.None
) : Field<String>(
    label = label,
    form = form,
    fieldState = fieldState,
    isVisible = isVisible,
    isEnabled = isEnabled,
    modifier = modifier,
    imeAction = imeAction,
    formatter = formatter,
    keyboardType = keyboardType,
    keyboardCapitalization = keyboardCapitalization,
    visualTransformation = visualTransformation,
) {
    @SuppressLint("NotConstructor")
    @Composable
    override fun Field() {
        this.updateComposableValue()
        if (!isVisible) {
            return
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = formatter?.invoke(value.value) ?: (value.value ?: ""),
            onValueChange = {
                onChange(it, form)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction ?: ImeAction.Next,
                keyboardType = keyboardType,
                capitalization = keyboardCapitalization
            ),
            keyboardActions = KeyboardActions.Default,
            enabled = isEnabled,
            isError = fieldState.hasError(),
            label = { Text(label, color = MaterialTheme.colorScheme.secondary) },
            interactionSource = remember { MutableInteractionSource() },
            visualTransformation = visualTransformation,
            placeholder = null,
            supportingText = {
                if (fieldState.hasError()) {
                    Text(
                        text = fieldState.errorText.joinToString("\n"),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
                    )
                }
            }
        )
    }
}