package ru.aip.intern.ui.state

import ru.aip.intern.domain.assessment.data.InternsComparison

data class InternsAssessmentState(
    val isRefreshing: Boolean = false,
    val assessmentsData: InternsComparison = InternsComparison(emptyList())
)