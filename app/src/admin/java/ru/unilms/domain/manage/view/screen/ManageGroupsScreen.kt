package ru.unilms.domain.manage.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.manage.model.Group
import ru.unilms.domain.manage.viewmodel.ManageGroupsViewModel
import java.util.UUID

@Composable
fun ManageGroupsScreen(
    navigate: (Screens, UUID?) -> Unit,
    onComposing: (AppBarState, FabState) -> Unit,
) {
    val viewModel = hiltViewModel<ManageGroupsViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var groups by remember { mutableStateOf<List<Group>?>(null) }

    fun updateGroups() = coroutineScope.launch {
        groups = viewModel.getGroups()
    }

    LaunchedEffect(true) {
        updateGroups()
        onComposing(
            AppBarState(),
            FabState()
        )
    }

    if (groups != null) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = groups!!) { group ->
                ListItem(
                    overlineContent = {
                        Text(
                            text = stringResource(
                                R.string.label_current_out_of_semester,
                                group.currentSemester,
                                group.maxSemester
                            )
                        )
                    },
                    headlineContent = { Text(text = group.name) },
                    supportingContent = {
                        Text(
                            text = pluralStringResource(
                                R.plurals.label_amount_of_students,
                                group.amountOfStudents,
                                group.amountOfStudents,
                            )
                        )
                    },
                    modifier = Modifier.clickable {
                        navigate(Screens.ManageGroup, group.id)
                    }
                )
            }
        }
    }



    Row(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(onClick = { navigate(Screens.CreateGroup, null) }) {
            Text(text = stringResource(id = R.string.button_create))
        }
    }
}