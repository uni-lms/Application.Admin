package ru.aip.intern.ui.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import ru.aip.intern.domain.calendar.data.DayEventsOverview
import java.time.LocalDate

@Composable
fun Day(
    day: CalendarDay,
    today: LocalDate,
    dayEventsOverview: DayEventsOverview?,
    onDayClick: () -> Unit
) {

    val enabled = day.position == DayPosition.MonthDate
    val todayBg = MaterialTheme.colorScheme.secondaryContainer

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = enabled, onClick = onDayClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                modifier = if (enabled && day.date == today) Modifier
                    .drawBehind {
                        drawCircle(
                            color = todayBg,
                            radius = 25f
                        )
                    } else Modifier,
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
                    dayEventsOverview?.let {
                        if (it.hasDeadlines) {
                            Badge(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        }

                        if (it.hasEvents) {
                            Badge(containerColor = MaterialTheme.colorScheme.errorContainer)
                        }
                    }
                }
            }

        }
    }

}