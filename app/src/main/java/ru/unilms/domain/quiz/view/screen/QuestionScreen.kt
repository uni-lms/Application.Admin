package ru.unilms.domain.quiz.view.screen

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
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.unilms.R
import ru.unilms.data.AppBarState
import ru.unilms.data.FabState
import ru.unilms.domain.app.util.Screens
import ru.unilms.domain.common.extension.toMutableStateList
import ru.unilms.domain.common.form.dynamic.FieldType
import ru.unilms.domain.common.form.dynamic.FormField
import ru.unilms.domain.quiz.model.ChosenAnswer
import ru.unilms.domain.quiz.model.QuestionInfo
import ru.unilms.domain.quiz.view.form.QuestionForm
import ru.unilms.domain.quiz.viewmodel.QuestionViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    attemptId: UUID,
    questionNumber: Int,
    onComposing: (AppBarState, FabState) -> Unit,
    navigate: (Screens, UUID, Int?, Boolean) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<QuestionViewModel>()
    var questionInfo: QuestionInfo by remember {
        mutableStateOf(
            viewModel.emptyQuestion
        )
    }

    var formState by remember { mutableStateOf(mutableStateListOf<FormField>()) }

    fun saveAnswer() = coroutineScope.launch {
        viewModel.saveAnswer(attemptId, questionInfo.id, formState.map {
            ChosenAnswer(it.id)
        })
    }

    fun finishAttempt() {
        saveAnswer()
        coroutineScope.launch {
            viewModel.finishAttempt(attemptId)?.let {
                navigate(
                    Screens.Quiz,
                    it.quizId!!,
                    null,
                    false
                )
            }
        }
    }


    LaunchedEffect(questionNumber) {
        questionInfo = viewModel.getQuestionInfo(attemptId, questionNumber)
        formState = questionInfo.selectedChoices.map {
            if (questionInfo.isMultipleChoicesAllowed) {
                FormField(it.title, it.id, FieldType.Checkbox)
            } else {
                FormField(it.title, it.id, FieldType.RadioButton)
            }
        }.toMutableStateList()
    }

    LaunchedEffect(questionInfo) {
        onComposing(
            AppBarState(
                title = questionInfo.quizTitle,
                actions = { }
            ),
            FabState(
                fab = {}
            )
        )
    }

    val formFields = questionInfo.choices.map {
        if (questionInfo.isMultipleChoicesAllowed) {
            FormField(it.title, it.id, FieldType.Checkbox)
        } else {
            FormField(it.title, it.id, FieldType.RadioButton)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
                    onClick = {
                        saveAnswer()
                        navigate(Screens.QuizAttempt, attemptId, it, false)
                    },
                    selected = it == questionNumber,
                    label = { Text(text = it.toString()) }
                )
            }
        }

        Text(
            text = stringResource(R.string.title_question_number, questionNumber),
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = questionInfo.questionTitle)
        QuestionForm(formState, formFields)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = {
                    saveAnswer()
                    navigate(
                        Screens.QuizAttempt,
                        attemptId,
                        questionNumber - 1,
                        false
                    )
                },
                enabled = questionNumber > 1
            ) {
                Icon(imageVector = Icons.Outlined.ChevronLeft, contentDescription = null)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.button_question_prev))
            }
            if (questionNumber < questionInfo.amountOfQuestions) {
                FilledTonalButton(
                    onClick = {
                        saveAnswer()
                        navigate(
                            Screens.QuizAttempt,
                            attemptId,
                            questionNumber + 1,
                            false
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.button_question_next))
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
                }
            } else {
                FilledTonalButton(
                    onClick = {
                        finishAttempt()
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.Send, contentDescription = null)
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.button_finish_attempt))
                }
            }
        }

    }
}