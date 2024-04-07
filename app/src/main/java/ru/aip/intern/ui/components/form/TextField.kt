package ru.aip.intern.ui.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    label: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit
) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(modifier = Modifier.padding(vertical = 16.dp)) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(text = label)
            }


            OutlinedTextField(
                value = value,
                onValueChange = onValueChange
            )
        }
    }

}