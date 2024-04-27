package ru.aip.intern.ui.state

import ru.aip.intern.domain.content.assignment.data.AssignmentInfo
import ru.aip.intern.domain.content.assignment.data.SolutionsList
import java.time.LocalDateTime
import java.util.UUID

data class AssignmentState(
    val isRefreshing: Boolean = false,
    val assignment: AssignmentInfo = AssignmentInfo(
        id = UUID.randomUUID(),
        title = "",
        deadline = LocalDateTime.now(),
        description = "",
        fileId = UUID.randomUUID()
    ),
    val solutions: SolutionsList = SolutionsList(emptyList())
)