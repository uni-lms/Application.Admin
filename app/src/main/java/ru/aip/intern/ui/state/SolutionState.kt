package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.assignment.data.SolutionInfo
import java.time.LocalDateTime

data class SolutionState(
    val isRefreshing: Boolean = false,
    val solutionInfo: SolutionInfo = SolutionInfo(
        author = "",
        createdAt = LocalDateTime.now(),
        link = null,
        files = emptyList(),
        comments = emptyList()
    ),
    val commentText: String = "",
)
