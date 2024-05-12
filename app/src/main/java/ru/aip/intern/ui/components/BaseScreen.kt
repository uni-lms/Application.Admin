package ru.aip.intern.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.networking.ConnectivityObserver
import ru.aip.intern.viewmodels.TopBarViewModel

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    children: @Composable () -> Unit
) {

    Column(
        Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing,
                    delayMillis = 500
                )
            )
    ) {
        if (!LocalInspectionMode.current) {
            val viewModel: TopBarViewModel = hiltViewModel()
            val networkState by
            viewModel.networkState.collectAsState(ConnectivityObserver.Status.Available)

            AnimatedVisibility(
                visible = networkState != ConnectivityObserver.Status.Available,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut() + slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
            ) {
                NoInternetAvailable()
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(28.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement
            ) {
                children()
            }
        }
    }
}