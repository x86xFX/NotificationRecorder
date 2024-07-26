package me.theek.nr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.theek.nr.presentation.screen.PermissionScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.PERMISSION_SCREEN,
    ) {
        composable(route = AppRoutes.PERMISSION_SCREEN) {
            PermissionScreen()
        }
    }
}

object AppRoutes {
    const val PERMISSION_SCREEN = "permission_screen"
}