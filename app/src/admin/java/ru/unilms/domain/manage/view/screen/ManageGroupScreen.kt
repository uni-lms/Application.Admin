package ru.unilms.domain.manage.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.common.view.component.field.M3TextField
import ru.unilms.domain.manage.model.Group
import ru.unilms.domain.manage.viewmodel.ManageGroupViewModel
import java.util.UUID


@Composable
fun ManageGroupScreen(groupId: UUID, onComposing: (AppBarState, FabState) -> Unit) {

    val viewModel = hiltViewModel<ManageGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var group: Group? by remember { mutableStateOf(null) }

    fun updateGroup() = coroutineScope.launch {
        group = viewModel.getGroup(groupId)
    }

    LaunchedEffect(true) {
        updateGroup()
        onComposing(
            AppBarState(),
            FabState()
        )
    }

    LaunchedEffect(group) {
        group?.let { viewModel.initForm(it) }
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        M3TextField(
            label = stringResource(R.string.label_name),
            form = viewModel.form,
            fieldState = viewModel.form.name
        ).Field()
        M3TextField(
            label = stringResource(R.string.label_current_semester),
            form = viewModel.form,
            fieldState = viewModel.form.currentSemester,
            keyboardType = KeyboardType.Decimal
        ).Field()
        M3TextField(
            label = stringResource(R.string.label_maximum_semester),
            form = viewModel.form,
            fieldState = viewModel.form.maxSemester,
            keyboardType = KeyboardType.Decimal
        ).Field()

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                coroutineScope.launch { viewModel.updateGroup(groupId) }
            }) {
                Text(text = stringResource(id = R.string.button_save))
            }
        }
    }
}