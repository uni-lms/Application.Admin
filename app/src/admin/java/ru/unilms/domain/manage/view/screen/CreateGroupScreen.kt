package ru.unilms.domain.manage.view.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.domain.manage.viewmodel.CreateGroupViewModel

@Composable
fun CreateGroupScreen() {
    val viewModel = hiltViewModel<CreateGroupViewModel>()

}