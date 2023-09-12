package ru.unilms.components.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CenteredRegularHeadline(text: String, modifier: Modifier = Modifier) {
    RegularHeadline(text = text, textAlign = TextAlign.Center, modifier = modifier)
}