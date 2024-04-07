package ru.aip.intern.ui.components.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.Badge
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun <TId> SingleSelectComboBox(
    icon: ImageVector,
    title: String,
    items: List<ComboBoxItem<TId>>,
    modifier: Modifier = Modifier,
    onSelectionChange: (TId) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<ComboBoxItem<TId>?>(null) }

    fun onSelectedUpdate(item: ComboBoxItem<TId>) {
        selectedItem.value = item
        onSelectionChange(item.id)
    }

    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = title)
        }
        BoxWithConstraints(
            modifier = Modifier.clickable {
                expanded = !expanded
            }
        ) {
            val width = this.maxWidth

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
                    .clickable { expanded = !expanded }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (selectedItem.value != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                                Text(text = selectedItem.value!!.name)
                            }
                        }
                    } else {
                        Text(text = "Выберите…")
                    }

                    Icon(
                        imageVector = if (expanded) Icons.Outlined.ArrowDropUp else Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                expanded = !expanded
                            }
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(width)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            onSelectedUpdate(item)
                        },
                        leadingIcon = {
                            RadioButton(
                                selected = selectedItem.value == item,
                                onClick = { onSelectedUpdate(item) })
                        }
                    )
                }
            }
        }
    }


}