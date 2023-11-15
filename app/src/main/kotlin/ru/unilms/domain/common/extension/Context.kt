package ru.unilms.domain.common.extension

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

internal fun Context.hasPermissions(vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}