package ru.aip.intern.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.R
import ru.aip.intern.domain.internships.data.Content
import ru.aip.intern.domain.internships.data.UserRole
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.ui.components.content.ContentCard
import ru.aip.intern.viewmodels.InternshipViewModel
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternshipScreen(
    title: MutableState<String>,
    internshipId: UUID,
    goToScreen: (Screen, UUID?) -> Unit
) {

    val viewModel = hiltViewModel<InternshipViewModel, InternshipViewModel.Factory>(
        creationCallback = { factory -> factory.create(internshipId) }
    )
    val refreshing = viewModel.isRefreshing.observeAsState(false)
    val internshipData =
        viewModel.internshipData.observeAsState(Content(title = "", sections = emptyList()))

    val userRole by viewModel.userRole.observeAsState(UserRole.Intern)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.refresh(internshipId) }
    )
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        if (internshipData.value.title.isEmpty()) {
            title.value = context.getString(R.string.internship)
        }
    }

    LaunchedEffect(internshipData.value.title) {
        if (internshipData.value.title.isNotEmpty()) {
            title.value = internshipData.value.title
        }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        BaseScreen {

            internshipData.value.sections.forEach { section ->
                if (section.items.isNotEmpty()) {
                    Text(
                        text = section.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    section.items.forEach { content ->
                        ContentCard(content = content) { screen, id ->
                            goToScreen(screen, id)
                        }
                    }
                }
            }

            if (userRole != UserRole.Intern) {
                Button(onClick = { goToScreen(Screen.InternsAssessment, null) }) {
                    Text(text = stringResource(R.string.interns_assessment))
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