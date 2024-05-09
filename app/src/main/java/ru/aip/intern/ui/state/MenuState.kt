package ru.aip.intern.ui.state

import ru.aip.intern.domain.auth.data.WhoamiResponse
import ru.aip.intern.domain.internal.data.ReleaseInfo

data class MenuState(
    val isRefreshing: Boolean = false,
    val isShowSheet: Boolean = false,
    val whoAmIData: WhoamiResponse = WhoamiResponse(email = "", fullName = ""),
    val notificationsCount: Int = 0,
    val releaseInfo: ReleaseInfo? = null,
    val downloadButtonState: DownloadButtonState = DownloadButtonState()
)