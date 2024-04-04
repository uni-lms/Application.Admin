package ru.aip.intern.ui.components.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleValueDisplay(
    title: String,
    value: String?,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = title, modifier = Modifier.padding(vertical = 16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
                    .clickable { onButtonClick() }
                    .weight(8f)
            ) {
                Text(text = if (value.isNullOrBlank()) "Выберите…" else value)
            }

            TextButton(onClick = onButtonClick, Modifier.weight(2f)) {
                Text(text = "Выбрать")
            }

        }
    }
}