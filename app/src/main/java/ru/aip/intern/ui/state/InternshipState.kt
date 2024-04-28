package ru.aip.intern.ui.state

import ru.aip.intern.domain.internships.data.Content
import ru.aip.intern.domain.internships.data.UserRole

data class InternshipState(
    val isRefreshing: Boolean = false,
    val contentData: Content = Content(title = "", sections = emptyList()),
    val userRole: UserRole = UserRole.Intern,
)
