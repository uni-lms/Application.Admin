package ru.unilms.ui.components.typography

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CenteredRegularHeadline(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    RegularHeadline(
        text = text,
        textAlign = TextAlign.Center,
        modifier = modifier,
        color = color
    )
}