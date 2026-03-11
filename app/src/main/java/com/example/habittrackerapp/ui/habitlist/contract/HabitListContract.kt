package com.example.habittrackerapp.ui.habitlist.contract

import com.example.habittrackerapp.domain.model.Habit

data class HabitListState(
    val habits: List<Habit> = emptyList(),
    val filteredHabits: List<Habit> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface HabitListIntent{
    data object LoadHabits: HabitListIntent
    data class ToggleHabit(val habitId: Int): HabitListIntent
    data class DeleteHabit(val habitId: Int): HabitListIntent
    data class SearchQueryChanged(val query: String): HabitListIntent
    data class SelectedHabit(val habitId: Int): HabitListIntent
    data class AddNewHabit(val name: String, val emoji: String): HabitListIntent
}

sealed interface HabitListEvent{
    data class ShowSnackBar(val message: String): HabitListEvent
    data class NavigateToDetail(val habitId: Int): HabitListEvent
}


