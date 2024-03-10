package ru.aip.intern.ui.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.aip.intern.navigation.Screen
import ru.aip.intern.ui.components.BaseScreen
import ru.aip.intern.viewmodels.StartScreenViewModel

@Composable
fun SplashScreen(onLoadingComplete: (Screen) -> Unit) {

    val viewModel: StartScreenViewModel = hiltViewModel()
    val startScreen by viewModel.startScreen.observeAsState()

    LaunchedEffect(startScreen) {
        startScreen?.let(onLoadingComplete)
    }

    BaseScreen {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}