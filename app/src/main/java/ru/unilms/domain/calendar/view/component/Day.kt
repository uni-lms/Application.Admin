package ru.unilms.domain.calendar.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import ru.unilms.domain.calendar.model.DayEventsInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Day(day: CalendarDay, eventsInfo: DayEventsInfo?, onDayClick: () -> Unit) {
    val enabled = day.position == DayPosition.MonthDate
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = enabled,
                onClick = onDayClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (enabled)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.outline
            )
            if (enabled) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    eventsInfo?.let {
                        if (it.regularEvents != 0) {
                            Badge(containerColor = MaterialTheme.colorScheme.primaryContainer)// {
//                                Text(text = it.regularEvents.toString())
//                            }
                        }
                        if (it.deadlines != 0) {
                            Badge(containerColor = MaterialTheme.colorScheme.errorContainer) //{
//                                Text(text = it.deadlines.toString())
                            //}
                        }
                        if (it.lessons != 0) {
                            Badge(containerColor = MaterialTheme.colorScheme.tertiaryContainer) //{
//                                Text(text = it.lessons.toString())
                            //}
                        }
                    }
                }
            }
        }
    }
}
