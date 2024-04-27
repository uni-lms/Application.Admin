package ru.aip.intern.ui.state

import ru.aip.intern.domain.internships.data.Internship

data class InternshipsState(
    val isRefreshing: Boolean = false,
    val internships: List<Internship> = emptyList()
)
