package ru.unilms.components.form

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ru.unilms.R
import ru.unilms.utils.forms.Field
import java.util.Calendar
import java.util.Date

class M3DateField(
    label: String,
    form: Form,
    modifier: Modifier? = Modifier,
    fieldState: FieldState<Date?>,
    isVisible: Boolean = true,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    formatter: ((raw: Date?) -> String)? = null,
) : Field<Date>(
    label = label,
    form = form,
    fieldState = fieldState,
    isVisible = isVisible,
    isEnabled = isEnabled,
    modifier = modifier,
    imeAction = imeAction,
    formatter = formatter
) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Field() {
        this.updateComposableValue()
        if (!isVisible) {
            return
        }

        val focusRequester = FocusRequester()

        val calendar = Calendar.getInstance()
        calendar.time = value.value ?: Date()

        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
        var isDatePickerOpened by remember { mutableStateOf(false) }
        val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

        if (isDatePickerOpened) {
            DatePickerDialog(
                onDismissRequest = {
                    isDatePickerOpened = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            calendar.timeInMillis = datePickerState.selectedDateMillis!!
                            this.onChange(calendar.time, form)
                            isDatePickerOpened = false
                        },
                        enabled = confirmEnabled
                    ) {
                        Text(stringResource(R.string.button_ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isDatePickerOpened = false
                        }
                    ) {
                        Text(stringResource(R.string.button_cancel))
                    }
                }) {
                DatePicker(state = datePickerState)
            }
        }

        M3TextFieldComponent(
            modifier = modifier ?: Modifier,
            isEnabled = isEnabled,
            label = { Text(label, color = MaterialTheme.colorScheme.secondary) },
            text = formatter?.invoke(value.value) ?: value.value.toString(),
            hasError = fieldState.hasError(),
            errorText = fieldState.errorText,
            isReadOnly = true,
            focusRequester = focusRequester,
            focusChanged = {
                if (it.isFocused) {
                    isDatePickerOpened = true
                }
            }
        )
    }
}