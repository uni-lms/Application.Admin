package ru.unilms.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthName(month: CalendarMonth) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        val monthName =
            month.yearMonth.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = month.yearMonth.year.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}