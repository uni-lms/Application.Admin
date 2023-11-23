package ru.unilms.domain.task.view.screen

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.task.viewmodel.SubmitAnswerViewModel
import java.io.File
import java.util.UUID


@Composable
fun SubmitAnswerScreen(
    taskId: UUID,
    onComposing: (AppBarState) -> Unit,
) {
    val viewModel = hiltViewModel<SubmitAnswerViewModel>()
    var selectedFiles: List<Uri> by remember { mutableStateOf(emptyList()) }
    val selectImagesActivityResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedFiles = emptyList()

                // If multiple files selected
                if (data?.clipData != null) {
                    val count = data.clipData?.itemCount ?: 0
                    for (i in 0 until count) {
                        val fileUri: Uri? = data.clipData?.getItemAt(i)?.uri
                        if (fileUri != null) {
                            selectedFiles += fileUri
                        }
                    }
                }
                // If single files selected
                else if (data?.data != null) {
                    val fileUri: Uri? = data.data
                    if (fileUri != null) {
                        selectedFiles += fileUri
                    }
                }
                Log.d("upload_files", selectedFiles.count().toString())
            }
        }
    val intent = Intent(ACTION_GET_CONTENT)
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    intent.type = "*/*"


    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_file_size_limit)) },
            trailingContent = {
                Text(
                    text = stringResource(id = R.string.label_megabytes, 50)
                )
            }
        )
        if (selectedFiles.isNotEmpty()) {
            Divider()
            Text(text = "Выбранные файлы", style = MaterialTheme.typography.titleMedium)
        }
        selectedFiles.forEach { uri ->
            val file = File(uri.path)
            ListItem(
                headlineContent = {
                    Text(text = file.name)
                },
                supportingContent = {
                    Text(text = stringResource(R.string.label_megabytes, file.length() / 1_000_000))
                },
                trailingContent = {
                    IconButton(
                        onClick = {
                            selectedFiles = selectedFiles.filter { it != uri }
                        }
                    ) {
                        Icon(Icons.Outlined.Remove, null)
                    }
                }
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                selectImagesActivityResult.launch(intent)
            }) {
                Text(text = "Выбрать файлы")
            }
        }
    }
}