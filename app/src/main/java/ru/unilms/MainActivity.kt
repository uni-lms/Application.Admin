package ru.unilms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.unilms.domain.app.view.screen.UniApp
import ru.unilms.domain.common.view.theme.UNITheme
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Locale.setDefault(Locale("ru", "RU"))
        setContent {
            UNITheme {
                UniApp()
            }
        }
    }
}