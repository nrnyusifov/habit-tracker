package com.example.habittrackerapp.ui.habitdetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HabitDetailRoute(
    viewModel: HabitDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    HabitDetailScreen(
        state = state,
        onIntent = viewModel::handleIntent,
        onBackClick = onBackClick
    )
}