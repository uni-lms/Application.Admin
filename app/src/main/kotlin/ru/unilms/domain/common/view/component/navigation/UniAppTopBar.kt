package ru.unilms.domain.common.view.component.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.unilms.R
import ru.unilms.domain.app.util.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniAppTopBar(
    currentScreen: Screens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    actions: @Composable() RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                title ?: stringResource(id = currentScreen.title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.screen_back_button)
                    )
                }
            }
        },
        actions = actions
    )

}

@Preview
@Composable
fun UniAppTopBarPreview() {
    UniAppTopBar(
        currentScreen = Screens.SignUp,
        canNavigateBack = true,
        navigateUp = { },
        actions = {}
    )
}