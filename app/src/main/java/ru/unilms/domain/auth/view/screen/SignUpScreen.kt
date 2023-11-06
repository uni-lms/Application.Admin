package ru.unilms.domain.auth.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.benlu.composeform.formatters.dateShort
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.auth.view.component.ImagePickerField
import ru.unilms.domain.auth.viewmodel.SignUpViewModel
import ru.unilms.domain.common.view.component.field.M3DateField
import ru.unilms.domain.common.view.component.field.M3PickerField
import ru.unilms.domain.common.view.component.field.M3TextField
import java.util.UUID

@Composable
fun SignUpScreen(navigate: (Screens, UUID?) -> Unit, onComposing: (AppBarState) -> Unit) {

    val viewModel = hiltViewModel<SignUpViewModel>()

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                actions = { }
            )
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ImagePickerField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                lastSelectedImage = viewModel.pickedImage.value,
                onSelection = {
                    viewModel.pickedImage.value = it
                }
            )
            M3TextField(
                label = stringResource(id = R.string.field_last_name),
                form = viewModel.form,
                fieldState = viewModel.form.lastName,
                keyboardType = KeyboardType.Text,
                keyboardCapitalization = KeyboardCapitalization.Sentences,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.field_first_name),
                form = viewModel.form,
                fieldState = viewModel.form.firstName,
                keyboardType = KeyboardType.Text,
                keyboardCapitalization = KeyboardCapitalization.Sentences,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.field_patronymic),
                form = viewModel.form,
                fieldState = viewModel.form.patronymic,
                keyboardType = KeyboardType.Text,
                keyboardCapitalization = KeyboardCapitalization.Sentences,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.field_email),
                form = viewModel.form,
                fieldState = viewModel.form.email,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3DateField(
                label = stringResource(id = R.string.field_birth_date),
                form = viewModel.form,
                fieldState = viewModel.form.dateOfBirth,
                formatter = ::dateShort,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3PickerField(
                label = stringResource(R.string.field_gender),
                form = viewModel.form,
                fieldState = viewModel.form.gender,
                isSearchable = false
            ).Field()
            M3PickerField(
                label = stringResource(R.string.field_role),
                form = viewModel.form,
                fieldState = viewModel.form.role,
                isSearchable = false
            ).Field()

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.submit { navigate(Screens.Courses, null) } }) {
                    Text(text = stringResource(R.string.button_register))
                }
            }
        }
    }
}
