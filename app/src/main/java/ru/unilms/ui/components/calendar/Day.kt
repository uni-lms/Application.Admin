package ru.unilms.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import ru.unilms.domain.model.calendar.DayEventsInfo

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
                        if (it.hasEvents) {
                            Dot(color = Color.Blue)
                        }
                        if (it.hasDeadlines) {
                            Dot(color = Color.Green)
                        }
                        if (it.hasLessons) {
                            Dot(color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Dot(color: Color) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color)
            .size(3.dp)
    )
}