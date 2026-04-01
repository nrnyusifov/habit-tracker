package com.example.habittrackerapp.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittrackerapp.ui.dashboard.DashboardRoute
import com.example.habittrackerapp.ui.habitdetail.HabitDetailRoute
import com.example.habittrackerapp.ui.habitlist.HabitListRoute

@Composable
fun AppNavGraph(navController: NavHostController) {
    val snackBarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardRoute()
        }

        composable(Screen.HabitList.route) {
            HabitListRoute(
                snackBarHostState = snackBarHostState,
                onNavigateToDetail = { habitId ->
                    navController.navigate(Screen.HabitDetail.createRoute(habitId))
                }
            )
        }

        composable(Screen.Settings.route) {
            androidx.compose.material3.Text("Settings Screen")
        }

        composable(
            route = Screen.HabitDetail.route,
            arguments = listOf(
                navArgument("habitId") { type = NavType.IntType }
            )
        ) {
            HabitDetailRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}