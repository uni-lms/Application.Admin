package ru.unilms.components.global

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.unilms.R
import ru.unilms.app.UniAppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniAppTopBar(
    currentScreen: UniAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = currentScreen.title),
                style = MaterialTheme.typography.headlineMedium
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
        }
    )
}

@Preview
@Composable
fun UniAppTopBarPreview() {
    UniAppTopBar(
        currentScreen = UniAppScreen.SignUp,
        canNavigateBack = false,
        navigateUp = { })
}