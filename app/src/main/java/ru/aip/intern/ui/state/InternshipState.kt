package ru.aip.intern.ui.state

import ru.aip.intern.domain.internships.data.Content

data class InternshipState(
    val isRefreshing: Boolean = false,
    val contentData: Content = Content(title = "", sections = emptyList())
)
