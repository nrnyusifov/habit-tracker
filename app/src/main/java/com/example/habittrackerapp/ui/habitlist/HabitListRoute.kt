package com.example.habittrackerapp.ui.habitlist

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habittrackerapp.ui.habitlist.contract.HabitListEvent

@Composable
fun HabitListRoute(
    viewModel: HabitListViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    onNavigateToDetail: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is HabitListEvent.NavigateToDetail -> onNavigateToDetail(event.habitId)
                is HabitListEvent.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
            }
        }
    }

    HabitListScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}