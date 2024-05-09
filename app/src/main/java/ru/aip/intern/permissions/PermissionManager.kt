package ru.aip.intern.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.aip.intern.MainActivity
import ru.aip.intern.util.findActivity
import javax.inject.Inject

class PermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun requestPermission(permission: String) {
        val activity = context.findActivity()
        (activity as? MainActivity)?.getPermissionLauncher()?.launch(permission)
    }

    fun checkPermission(permission: String): PermissionStatus {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            permission
        )

        return if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.DENIED
        }
    }

}