package ru.aip.intern.downloading

import android.content.Context
import androidx.core.content.FileProvider
import ru.solrudev.ackpine.installer.PackageInstaller
import ru.solrudev.ackpine.installer.createSession
import ru.solrudev.ackpine.session.SessionResult
import ru.solrudev.ackpine.session.await
import java.io.File
import kotlin.coroutines.cancellation.CancellationException

data class LocalFile(
    val file: File,
    val mimeType: String,
    val name: String
)

suspend fun LocalFile.installApplication(context: Context) {

    if (this.mimeType != "application/vnd.android.package-archive") {
        throw IllegalArgumentException("Only android application files are supported!")
    }

    if (!this.file.isFile && !this.file.exists()) {
        throw IllegalArgumentException("Not a file or the file doesn't exist")
    }

    val authority = "${context.packageName}.fileprovider"
    val uri = FileProvider.getUriForFile(context, authority, this.file)
    try {
        when (val result = PackageInstaller.getInstance(context).createSession(uri).await()) {
            is SessionResult.Success -> println("Success")
            is SessionResult.Error -> println(result.cause.message)
        }
    } catch (_: CancellationException) {
        println("Cancelled")
    } catch (exception: Exception) {
        println(exception)
    }
}
