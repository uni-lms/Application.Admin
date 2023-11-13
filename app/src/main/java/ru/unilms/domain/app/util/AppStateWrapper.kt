package ru.unilms.domain.app.util

import ru.unilms.data.AppBarState
import ru.unilms.data.FabState

data class AppStateWrapper(
    var appBarState: AppBarState,
    var fabState: FabState,
)
