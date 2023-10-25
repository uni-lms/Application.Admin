package ru.unilms.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Rule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.unilms.app.UniAppScreen
import ru.unilms.data.AppBarState
import ru.unilms.ui.components.form.FieldType
import ru.unilms.ui.components.form.FormField
import ru.unilms.ui.forms.QuestionForm
import ru.unilms.viewmodels.QuestionViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    attemptId: UUID,
    questionNumber: Int,
    onComposing: (AppBarState) -> Unit,
    navigate: (UniAppScreen, UUID, Int?, Boolean) -> Unit
) {

    val viewModel = hiltViewModel<QuestionViewModel>()

    val questionInfo = viewModel.getQuestionInfo(questionNumber)
    val formFields = questionInfo.answers.map {
        if (questionInfo.isMultipleChoicesAllowed) {
            FormField(it.title, it.id, FieldType.Checkbox)
        } else {
            FormField(it.title, it.id, FieldType.RadioButton)
        }
    }

    val formState = remember { mutableStateListOf<FormField>() }

    LaunchedEffect(true) {
        onComposing(
            AppBarState(
                title = questionInfo.quizTitle,
                actions = {
                    IconButton(onClick = {
                        navigate(
                            UniAppScreen.QuizAttemptResults,
                            attemptId,
                            null,
                            false
                        )
                    }) {
                        Icon(imageVector = Icons.Outlined.Rule, contentDescription = null)
                    }
                }
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            (1..questionInfo.amountOfQuestions).forEach {
                FilterChip(
                    onClick = { navigate(UniAppScreen.QuizAttempt, attemptId, it, false) },
                    selected = it == questionNumber,
                    label = { Text(text = it.toString()) }
                )
            }
        }

        Column(Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "Вопрос №$questionNumber", style = MaterialTheme.typography.titleLarge)
            Text(text = questionInfo.questionTitle)
            QuestionForm(formState, formFields)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = {
                    navigate(
                        UniAppScreen.QuizAttempt,
                        attemptId,
                        questionNumber - 1,
                        false
                    )
                },
                enabled = questionNumber > 1
            ) {
                Icon(imageVector = Icons.Outlined.ChevronLeft, contentDescription = null)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Назад")
            }
            FilledTonalButton(
                onClick = {
                    navigate(
                        UniAppScreen.QuizAttempt,
                        attemptId,
                        questionNumber + 1,
                        false
                    )
                },
                enabled = questionNumber < questionInfo.amountOfQuestions
            ) {
                Text(text = "Вперёд")
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
            }
        }

    }
}