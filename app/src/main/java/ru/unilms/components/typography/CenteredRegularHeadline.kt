package ru.unilms.components.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CenteredRegularHeadline(text: String) {
    RegularHeadline(text = text, textAlign = TextAlign.Center)
}