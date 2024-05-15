package ru.aip.intern.ui.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import ru.aip.intern.domain.content.quiz.data.Choice

@Composable
fun SelectRadioOrCheck(
    selectedChoices: SnapshotStateList<Choice>,
    isMultipleChoicesAllowed: Boolean,
    choices: List<Choice>
) {

    fun onChoiceSelected(choice: Choice) {
        if (!isMultipleChoicesAllowed) {
            selectedChoices.clear()
        }

        if (choice in selectedChoices) {
            selectedChoices.remove(choice)
        } else {
            selectedChoices.add(choice)
        }

    }

    Column {
        choices.forEach { choice ->
            ListItem(
                leadingContent = {
                    if (isMultipleChoicesAllowed) {
                        Checkbox(checked = choice in selectedChoices, onCheckedChange = null)
                    } else {
                        RadioButton(selected = choice in selectedChoices, onClick = null)
                    }
                },
                headlineContent = { Text(text = choice.text) },
                modifier = Modifier
                    .selectable(
                        selected = choice in selectedChoices,
                        onClick = {
                            onChoiceSelected(choice)
                        },
                    )

            )
        }
    }
}