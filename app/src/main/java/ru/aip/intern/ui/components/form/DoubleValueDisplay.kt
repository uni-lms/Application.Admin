package ru.aip.intern.ui.components.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.aip.intern.R

@Composable
fun DoubleValueDisplay(
    icon: ImageVector,
    title: String,
    leftValue: String?,
    rightValue: String?,
    modifier: Modifier = Modifier,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    divider: @Composable () -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = title)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
                    .clickable { onLeftClick() }
                    .weight(8f)
            ) {
                Text(text = if (leftValue.isNullOrBlank()) stringResource(R.string.choose) else leftValue)
            }

            divider()

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
                    .clickable { onRightClick() }
                    .weight(8f)
            ) {
                Text(text = if (rightValue.isNullOrBlank()) stringResource(R.string.choose) else rightValue)
            }
        }
    }
}