package ru.unilms.domain.app.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ru.unilms.R

enum class Screens(
    val title: Int = R.string.app_name,
    val canGoBack: Boolean = true,
    val showBottomAppBar: Boolean = false,
    val icon: ImageVector? = null,
    val showInBottomAppBar: Boolean = true,
    val showInMenu: Boolean = false,
) {
    SelectApiUri(R.string.screen_server_select, false, showInBottomAppBar = false),
    LoginOrRegister(showInBottomAppBar = false),
    Login(R.string.screen_login, showInBottomAppBar = false),
    SignUp(R.string.screen_registration, showInBottomAppBar = false),
    Manage(R.string.screen_manage, false, true, Icons.Outlined.ManageAccounts, true, false),
    Menu(R.string.screen_menu, false, true, Icons.Outlined.Menu),
    Settings(R.string.screen_settings, true, true, Icons.Filled.Settings, false),
}