package ru.unilms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.unilms.app.UniApp
import ru.unilms.ui.theme.UNITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UNITheme {
                UniApp()
            }
        }
    }
}