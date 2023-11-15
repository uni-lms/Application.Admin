package ru.unilms.domain.quiz.view.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ru.unilms.domain.common.form.dynamic.FieldType
import ru.unilms.domain.common.form.dynamic.FormField

@Composable
fun QuestionForm(
    formState: SnapshotStateList<FormField>,
    formFields: List<FormField>,
) {

    fun onOptionSelected(
        formState: SnapshotStateList<FormField>,
        field: FormField,
        isMultipleAllowed: Boolean,
    ) {
        if (!isMultipleAllowed) {
            formState.clear()
        }

        if (field in formState) {
            formState.remove(field)
        } else {
            formState.add(field)
        }
    }

    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        formFields.forEach { field ->
            when (field.type) {
                FieldType.RadioButton -> {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 56.dp)
                            .selectable(
                                selected = (field in formState),
                                onClick = { onOptionSelected(formState, field, false) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (field in formState),
                            onClick = null
                        )
                        Text(
                            text = field.label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                FieldType.Checkbox -> {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (field in formState),
                                onClick = { onOptionSelected(formState, field, true) },
                                role = Role.Checkbox
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = (field in formState),
                            onCheckedChange = null
                        )
                        Text(
                            text = field.label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}