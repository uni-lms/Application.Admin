package ru.unilms.domain.manage.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.common.view.component.field.M3TextField
import ru.unilms.domain.manage.model.User
import ru.unilms.domain.manage.viewmodel.ManageUserViewModel
import java.util.UUID

@Composable
fun ManageUserScreen(
    userId: UUID,
    onComposing: (AppBarState, FabState) -> Unit,
) {

    val viewModel = hiltViewModel<ManageUserViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var user: User? by remember { mutableStateOf(null) }

    fun updateUserInfo(userId: UUID) = coroutineScope.launch {
        user = viewModel.getUser(userId)
    }

    LaunchedEffect(true) {
        updateUserInfo(userId)
        onComposing(
            AppBarState(),
            FabState()
        )
    }

    LaunchedEffect(user) {
        user?.let { viewModel.initForm(it) }
    }


    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        M3TextField(
            form = viewModel.form,
            fieldState = viewModel.form.lastNameField,
            label = stringResource(R.string.field_last_name),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            keyboardCapitalization = KeyboardCapitalization.Sentences,
        ).Field()
        M3TextField(
            form = viewModel.form,
            fieldState = viewModel.form.firstNameField,
            label = stringResource(R.string.field_first_name),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            keyboardCapitalization = KeyboardCapitalization.Sentences,

            ).Field()
        M3TextField(
            form = viewModel.form,
            fieldState = viewModel.form.patronymicField,
            label = stringResource(R.string.field_patronymic),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            keyboardCapitalization = KeyboardCapitalization.Sentences,
        ).Field()
        M3TextField(
            form = viewModel.form,
            fieldState = viewModel.form.roleNameField,
            label = stringResource(id = R.string.field_role),
            isEnabled = false
        ).Field()

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.button_save))
            }
        }
    }

}