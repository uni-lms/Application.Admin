package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.form.DoubleValueDisplay
import ru.aip.intern.ui.components.form.MultiSelectComboBox
import ru.aip.intern.ui.components.form.SingleValueDisplay
import ru.aip.intern.ui.components.form.TimePickerDialog
import ru.aip.intern.ui.components.form.toComboBoxItemList
import ru.aip.intern.util.epochDateToNullableString
import ru.aip.intern.util.formatTimeFromPair
import ru.aip.intern.util.is24HourFormat
import ru.aip.intern.viewmodels.CreateEventViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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

    val datePickerState = rememberDatePickerState()
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val startTimePickerState = rememberTimePickerState(is24Hour = is24HourFormat(context))
    val endTimePickerState = rememberTimePickerState(is24Hour = is24HourFormat(context))

    var showStartTimePicker by rememberSaveable { mutableStateOf(false) }
    var showEndTimePicker by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {

            SingleValueDisplay(
                title = "Дата события",
                value = datePickerState.selectedDateMillis.epochDateToNullableString()
            ) {
                showDatePicker = true
            }

            DoubleValueDisplay(
                title = "Время события",
                leftValue = Pair(startTimePickerState.hour, startTimePickerState.minute)
                    .formatTimeFromPair(
                        context
                    ),
                rightValue = Pair(endTimePickerState.hour, endTimePickerState.minute)
                    .formatTimeFromPair(
                        context
                    ),
                divider = {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .width(10.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                onLeftClick = { showStartTimePicker = true },
                onRightClick = { showEndTimePicker = true }
            )

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

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Выбрать")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Отменить")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (showStartTimePicker) {
                TimePickerDialog(
                    title = "Выберите время",
                    onDismissRequest = { showStartTimePicker = false },
                    onConfirm = { showStartTimePicker = false }
                ) {
                    TimePicker(
                        state = startTimePickerState,
                        layoutType = TimePickerLayoutType.Vertical,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }

            if (showEndTimePicker) {
                TimePickerDialog(
                    title = "Выберите время",
                    onDismissRequest = { showEndTimePicker = false },
                    onConfirm = { showEndTimePicker = false }
                ) {
                    TimePicker(
                        state = endTimePickerState,
                        layoutType = TimePickerLayoutType.Vertical,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }

        }
        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}