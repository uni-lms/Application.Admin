package ru.unilms.domain.app.util

import androidx.navigation.NavHostController
import java.util.UUID

fun goToScreen(
    navController: NavHostController,
    screen: Screens,
    id: UUID? = null,
    saveEntry: Boolean = true,
) {
    val route: String = if (id == null) {
        screen.name
    } else {
        "${screen.name}/$id"
    }
    navController.navigate(route) {
        if (!saveEntry) {
            popUpTo(navController.currentDestination?.route ?: "") {
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun goToScreen(
    navController: NavHostController,
    screen: Screens,
    id: UUID? = null,
    questionNumber: Int? = null,
    saveEntry: Boolean = true,
) {
    if (questionNumber == null) {
        return goToScreen(navController, screen, id, saveEntry)
    }

    navController.navigate("${screen.name}/$id/$questionNumber") {
        if (!saveEntry) {
            popUpTo(navController.currentDestination?.route ?: "") {
                inclusive = false
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}