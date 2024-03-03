package ru.aip.intern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.aip.intern.ui.screens.AipApp
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Locale.setDefault(Locale("ru", "RU"))
        setContent {
            AltenarInternshipTheme {
                AipApp()
            }
        }
    }
}