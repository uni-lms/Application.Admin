package ru.unilms.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.benlu.composeform.formatters.dateShort
import ru.unilms.MainViewModel
import ru.unilms.R
import ru.unilms.components.form.M3DateField
import ru.unilms.components.form.M3PickerField
import ru.unilms.components.form.M3TextField
import ru.unilms.components.typography.CenteredRegularHeadline

@Composable
fun SignUpScreen() {

    val viewModel = hiltViewModel<MainViewModel>()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CenteredRegularHeadline(text = stringResource(R.string.register))
            M3TextField(
                label = stringResource(id = R.string.last_name),
                form = viewModel.form,
                fieldState = viewModel.form.lastName,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.first_name),
                form = viewModel.form,
                fieldState = viewModel.form.firstName,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.patronymic),
                form = viewModel.form,
                fieldState = viewModel.form.patronymic,
                keyboardType = KeyboardType.Text,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3TextField(
                label = stringResource(id = R.string.email),
                form = viewModel.form,
                fieldState = viewModel.form.email,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3DateField(
                label = stringResource(id = R.string.birthDate),
                form = viewModel.form,
                fieldState = viewModel.form.dateOfBirth,
                formatter = ::dateShort,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ).Field()
            M3PickerField(
                label = "Пол",
                form = viewModel.form,
                fieldState = viewModel.form.gender,
                isSearchable = false
            ).Field()
            M3PickerField(
                label = "Роль",
                form = viewModel.form,
                fieldState = viewModel.form.role,
                isSearchable = false
            ).Field()
        }
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen()
}