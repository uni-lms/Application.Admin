package ru.unilms

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import ru.unilms.domain.app.view.screen.UniApp
import ru.unilms.domain.common.view.theme.UNITheme
import ru.unilms.domain.notifications.NotificationChannels
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enumValues<NotificationChannels>().forEach {
            val channel = NotificationChannel(
                it.stringId,
                applicationContext.resources.getString(it.title),
                it.importance
            )
            channel.description = applicationContext.resources.getString(it.description)

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        requestMultiplePermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            var isGranted = false
            var permission = ""
            it.forEach { (s, b) ->
                isGranted = b
                permission = s
            }

            if (!isGranted) {
                Toast.makeText(this, "Нет разрешения $permission", Toast.LENGTH_SHORT).show()
            }
        }

        Locale.setDefault(Locale("ru", "RU"))
        setContent {
            UNITheme {
                val permissions = mutableListOf<String>()
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                }
                requestMultiplePermission.launch(
                    permissions.toTypedArray()
                )
                UniApp()
            }
        }
    }
}