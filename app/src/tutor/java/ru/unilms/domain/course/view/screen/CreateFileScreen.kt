package ru.unilms.domain.course.view.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.common.view.component.field.M3TextField
import ru.unilms.domain.course.model.Block
import ru.unilms.domain.course.view.form.CreateFileForm
import ru.unilms.domain.course.viewmodel.CreateFileViewModel
import java.util.UUID

@Composable
fun CreateFileScreen(
    courseId: UUID,
    navigate: (Screens, UUID?) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<CreateFileViewModel>()
    val uri: Uri? by remember {
        mutableStateOf(null)
    }

    var blocks: List<Block> by remember {
        mutableStateOf(listOf())
    }

    val form = CreateFileForm(blocks.toMutableList())

    fun updateBlocks() = coroutineScope.launch {
        blocks = viewModel.getBlocks()
    }

    LaunchedEffect(true) {
        updateBlocks()
    }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        M3TextField(
            label = stringResource(R.string.field_visible_name),
            form = form,
            fieldState = form.visibleNameField
        ).Field()
    }
}