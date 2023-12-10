package ru.unilms.domain.course.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.common.view.component.field.M3PickerField
import ru.unilms.domain.common.view.component.field.M3TextField
import ru.unilms.domain.course.model.Block
import ru.unilms.domain.course.view.form.CreateCourseForm
import ru.unilms.domain.course.viewmodel.CreateCourseViewModel
import ru.unilms.domain.manage.model.Group
import java.util.UUID

@Composable
fun CreateCourseScreen(
    navigate: (Screens, UUID) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<CreateCourseViewModel>()

    var blocks: List<Block> by remember {
        mutableStateOf(listOf())
    }
    var groups: List<Group> by remember {
        mutableStateOf(listOf())
    }

    val form =
        CreateCourseForm(groups.toMutableList(), blocks.toMutableList(), LocalContext.current)

    fun updateBlocks() = coroutineScope.launch {
        blocks = viewModel.getBlocks()
    }

    fun updateGroups() = coroutineScope.launch {
        groups = viewModel.getGroups()
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            ),
            FabState(
                fab = {}
            )
        )
        updateGroups()
        updateBlocks()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        M3TextField(
            label = stringResource(R.string.label_name),
            form = form,
            fieldState = form.nameField
        ).Field()
        M3TextField(
            label = stringResource(R.string.label_abbreviation),
            form = form,
            fieldState = form.abbreviationField
        ).Field()
        M3TextField(
            label = stringResource(R.string.label_semester),
            form = form,
            fieldState = form.semesterField,
            keyboardType = KeyboardType.Decimal
        ).Field()
        M3PickerField(
            label = stringResource(R.string.screen_groups),
            form = form,
            fieldState = form.groupsField,
            isSearchable = true
        ).Field()

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.createCourse(form, { id -> navigate(Screens.Course, id) })
                }
            }) {
                Text(text = stringResource(R.string.button_save))
            }
        }
    }
}