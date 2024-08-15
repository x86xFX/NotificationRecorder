package me.theek.nr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.theek.nr.presentation.screen.NotificationDetailedScreen
import me.theek.nr.presentation.screen.NotificationScreen
import me.theek.nr.presentation.screen.PermissionScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.PERMISSION_SCREEN,
    ) {
        composable(route = AppRoutes.PERMISSION_SCREEN) {
            PermissionScreen(
                onNavigateToNotificationScreen = {
                    if (navController.canGoBack) {
                        navController.navigate(AppRoutes.NOTIFICATION_SCREEN) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        composable(route = AppRoutes.NOTIFICATION_SCREEN) {
            NotificationScreen(
                onNotificationClicked = {
                    if (navController.canGoBack) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "notification_id",
                            value = it
                        )
                    }
                    navController.navigate(AppRoutes.NOTIFICATION_DETAILED_SCREEN)
                }
            )
        }

        composable(route = AppRoutes.NOTIFICATION_DETAILED_SCREEN) {
            val notificationId = remember { navController.previousBackStackEntry?.savedStateHandle?.get<Int>(key = "notification_id") }
            NotificationDetailedScreen(
                notificationId = notificationId,
                onNavigateBackClicked = {
                    if (navController.canGoBack) {
                        navController.navigate(AppRoutes.NOTIFICATION_SCREEN) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
    }
}

private val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

object AppRoutes {
    const val PERMISSION_SCREEN = "permission_screen"
    const val NOTIFICATION_SCREEN = "notification_screen"
    const val NOTIFICATION_DETAILED_SCREEN = "notification_detailed_screen"
}