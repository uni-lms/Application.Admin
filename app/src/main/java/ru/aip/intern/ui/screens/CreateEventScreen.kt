package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.form.MultiSelectComboBox
import ru.aip.intern.ui.components.form.toComboBoxItemList
import ru.aip.intern.viewmodels.CreateEventViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateEventScreen(title: MutableState<String>) {
    title.value = "Запланировать событие"

    val viewModel: CreateEventViewModel = hiltViewModel()
    val eventCreatingInfo by viewModel.eventCreatingInfo.observeAsState(viewModel.defaultEventCreatingInfo)
    val selectedInternships = remember { mutableStateListOf<UUID>() }
    val selectedUsers = remember { mutableStateListOf<UUID>() }

    val refreshing = viewModel.isRefreshing.observeAsState(false)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh() }
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {
            MultiSelectComboBox(
                title = "Выберите участвующие стажировки",
                items = eventCreatingInfo.internships.toComboBoxItemList({ it.id }, { it.name })
            ) {
                selectedInternships.clear()
                selectedInternships.addAll(it)
            }

            MultiSelectComboBox(
                title = "Выберите приглашённых преподавателей",
                items = eventCreatingInfo.users.toComboBoxItemList({ it.id }, { it.name })
            ) {
                selectedUsers.clear()
                selectedUsers.addAll(it)
            }
        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}