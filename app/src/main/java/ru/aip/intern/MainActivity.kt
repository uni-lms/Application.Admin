package ru.aip.intern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.Greeting
import ru.aip.intern.ui.theme.AltenarInternshipTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Locale.setDefault(Locale("ru", "RU"))
        setContent {
            AltenarInternshipTheme {
                BaseScreen {
                    Greeting("Android")
                }
            }
        }
    }
}