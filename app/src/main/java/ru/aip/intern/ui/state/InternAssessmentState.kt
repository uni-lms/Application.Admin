package ru.aip.intern.ui.state

import ru.aip.intern.domain.assessment.data.InternAssessment
import java.util.UUID

data class InternAssessmentState(
    val isRefreshing: Boolean = false,
    val assessmentData: InternAssessment = InternAssessment(
        UUID.randomUUID(),
        "",
        emptyList()
    )
)
