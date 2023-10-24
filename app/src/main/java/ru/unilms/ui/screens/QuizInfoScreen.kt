package ru.unilms.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.unilms.R
import ru.unilms.utils.units.minutes
import java.util.UUID

@Composable
fun QuizInfoScreen(quizId: UUID) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_amount_of_questions)) },
            trailingContent = {
                Text(
                    text = "10"
                )
            }
        )
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_amount_of_attempts)) },
            trailingContent = {
                Text(
                    text = "1"
                )
            }
        )
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.label_quiz_try_time_limit)) },
            trailingContent = {
                Text(
                    text = 59.minutes
                )
            }
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.label_quiz_begin_attempt))
        }
    }
}
