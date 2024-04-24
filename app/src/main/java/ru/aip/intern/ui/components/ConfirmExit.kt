package ru.aip.intern.ui.components

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ru.aip.intern.R

@Composable
fun ConfirmExit() {
    val context = LocalContext.current
    var lastBackPressTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentBackPressTime = System.currentTimeMillis()
        if (currentBackPressTime - lastBackPressTime > 2000) { // 2 seconds
            Toast.makeText(
                context,
                context.getString(R.string.tap_back_again_to_exit), Toast.LENGTH_SHORT
            ).show()
            lastBackPressTime = currentBackPressTime
        } else {
            (context as? Activity)?.finish()
        }
    }
}