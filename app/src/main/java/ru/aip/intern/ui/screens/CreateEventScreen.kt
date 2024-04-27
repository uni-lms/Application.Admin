package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.domain.calendar.data.EventType
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.form.ComboBoxItem
import ru.aip.intern.ui.components.form.DoubleValueDisplay
import ru.aip.intern.ui.components.form.MultiSelectComboBox
import ru.aip.intern.ui.components.form.SingleSelectComboBox
import ru.aip.intern.ui.components.form.SingleValueDisplay
import ru.aip.intern.ui.components.form.TextField
import ru.aip.intern.ui.components.form.TimePickerDialog
import ru.aip.intern.ui.components.form.toComboBoxItemList
import ru.aip.intern.util.epochDateToNullableString
import ru.aip.intern.util.formatTimeFromPair
import ru.aip.intern.util.is24HourFormat
import ru.aip.intern.viewmodels.CreateEventViewModel
import java.util.TimeZone
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(title: MutableState<String>, navigate: (Screen, UUID) -> Unit) {
    title.value = stringResource(R.string.create_event)

    val viewModel: CreateEventViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
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
            TextField(
                label = stringResource(R.string.event_title),
                icon = Icons.Outlined.Edit,
                value = state.formState.title
            ) {
                viewModel.updateFormStateTitle(it)
            }

            TextField(
                label = stringResource(R.string.event_link),
                icon = Icons.Outlined.Link,
                value = state.formState.link
            ) {
                viewModel.updateFormStateLink(it)
            }

            SingleValueDisplay(
                icon = Icons.Outlined.CalendarMonth,
                title = stringResource(R.string.event_date),
                value = datePickerState.selectedDateMillis.epochDateToNullableString()
            ) {
                showDatePicker = true
            }

            DoubleValueDisplay(
                icon = Icons.Outlined.AccessTime,
                title = stringResource(R.string.event_time),
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
                icon = Icons.Outlined.School,
                title = stringResource(R.string.internships),
                items = state.eventCreatingInfo.internships.toComboBoxItemList(
                    { it.id },
                    { it.name })
            ) {
                viewModel.updateFormStateSelectedInternships(it)
            }

            MultiSelectComboBox(
                icon = Icons.Outlined.PeopleOutline,
                title = stringResource(R.string.tutors),
                items = state.eventCreatingInfo.users.toComboBoxItemList({ it.id }, { it.name })
            ) {
                viewModel.updateFormStateSelectedUsers(it)
            }

            SingleSelectComboBox(
                icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
                title = stringResource(R.string.event_type),
                items = EventType.entries.filter { it.name != EventType.Deadline.name }
                    .mapIndexed { ind, item ->
                    ComboBoxItem(ind + 1, item.label)
                }
            ) {
                viewModel.updateFormStateEventType(it)
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    viewModel.createEvent(
                        datePickerState,
                        startTimePickerState,
                        endTimePickerState,
                        TimeZone.getDefault().toZoneId()
                    ) { screen, id -> navigate(screen, id) }
                }) {
                    Text(text = stringResource(R.string.create))
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text(stringResource(R.string.select))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (showStartTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showStartTimePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showStartTimePicker = false }) {
                            Text(stringResource(R.string.select))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showStartTimePicker = false }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
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
                    onDismissRequest = { showEndTimePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showEndTimePicker = false }) {
                            Text(stringResource(R.string.select))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEndTimePicker = false }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
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
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}