package ru.aip.intern.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.aip.intern.R
import ru.aip.intern.domain.assessment.data.Assessment
import ru.aip.intern.ui.state.InternAssessmentState
import ru.aip.intern.util.UiText
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternAssessmentScreen(
    state: InternAssessmentState,
    onRefresh: () -> Unit,
    onSaveButtonClick: (UUID, Int) -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = onRefresh
    )

    val isExpandedMap = remember {
        List(state.assessmentData.assessments.size) { index: Int -> index to false }
            .toMutableStateMap()
    }


    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(28.dp)
        ) {
            LazyColumn {
                state.assessmentData.assessments.onEachIndexed { index, assessment ->
                    section(
                        data = assessment,
                        isExpanded = isExpandedMap[index] ?: false,
                        onHeaderClick = {
                            isExpandedMap[index] = !(isExpandedMap[index] ?: false)
                        },
                        onSaveButtonClick = onSaveButtonClick
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

fun LazyListScope.section(
    data: Assessment,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit,
    onSaveButtonClick: (UUID, Int) -> Unit
) {
    item {

        ListItem(
            headlineContent = { Text(data.title) },
            supportingContent = {
                if (data.description != null) {
                    Text(data.description)
                }
            },
            trailingContent = {
                val icon = if (isExpanded) {
                    Icons.Outlined.KeyboardArrowUp
                } else {
                    Icons.Outlined.KeyboardArrowDown
                }

                Icon(imageVector = icon, contentDescription = null)
            },
            modifier = Modifier.clickable { onHeaderClick() }
        )

    }

    if (isExpanded) {
        item {

            var score by rememberSaveable { mutableStateOf(if (data.score == null) "" else data.score.toString()) }
            var isScoreWithError by rememberSaveable { mutableStateOf(false) }
            val defaultValue = UiText.DynamicText("").asString()
            var scoreErrorMessage by rememberSaveable { mutableStateOf(defaultValue) }
            val context = LocalContext.current
            val keyboardController = LocalSoftwareKeyboardController.current

            fun validateScore(score: Int): Boolean {
                return score in 0..10
            }

            Column {

                OutlinedTextField(
                    value = score,
                    onValueChange = { score = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    isError = isScoreWithError,
                    supportingText = {
                        if (isScoreWithError) {
                            Text(text = scoreErrorMessage)
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.score)
                        )
                    }
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        if (validateScore(score.toInt())) {
                            isScoreWithError = false
                            scoreErrorMessage = defaultValue
                            keyboardController?.hide()
                            onSaveButtonClick(data.id, score.toInt())
                        } else {
                            isScoreWithError = true
                            scoreErrorMessage =
                                UiText.StringResource(R.string.score_out_of_range).asString(context)
                        }
                    }) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}