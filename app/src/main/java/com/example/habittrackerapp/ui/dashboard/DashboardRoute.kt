package com.example.habittrackerapp.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardRoute(vm: DashboardViewModel = hiltViewModel())
{
    val state = vm.state.collectAsStateWithLifecycle().value
    DashboardScreen(
        state = state
    )
}