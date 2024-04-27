package ru.aip.intern.ui.state

import ru.aip.intern.domain.auth.data.WhoamiResponse

data class MenuState(
    val isRefreshing: Boolean = false,
    val whoAmIData: WhoamiResponse = WhoamiResponse(email = "", fullName = ""),
    val notificationsCount: Int = 0
)