package com.example.habittrackerapp.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.domain.model.Habit
import com.example.habittrackerapp.domain.repository.HabitRepository
import com.example.habittrackerapp.ui.habitlist.contract.HabitListEvent
import com.example.habittrackerapp.ui.habitlist.contract.HabitListIntent
import com.example.habittrackerapp.ui.habitlist.contract.HabitListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {
    private val _state = MutableStateFlow(HabitListState())
    val state: StateFlow<HabitListState> = _state.asStateFlow()

    private val _event = Channel<HabitListEvent>()
    val event: Flow<HabitListEvent> = _event.receiveAsFlow()

    init {
        handleIntent(HabitListIntent.LoadHabits)
    }

    fun handleIntent(intent: HabitListIntent) {
        when (intent) {
            is HabitListIntent.LoadHabits -> loadHabits()
            is HabitListIntent.ToggleHabit -> toggleHabit(intent.habitId)
            is HabitListIntent.DeleteHabit -> deleteHabit(intent.habitId)
            is HabitListIntent.SearchQueryChanged -> updateSearch(intent.query)
            is HabitListIntent.SelectedHabit -> navigateToDetail(intent.habitId)
            is HabitListIntent.AddNewHabit -> addNewHabit(intent.name, intent.emoji)
        }
    }

    private fun loadHabits() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                repository.getAllHabits().collect { habits ->
                    _state.update { currentState ->
                        currentState.copy(
                            habits = habits,
                            filteredHabits = filterHabits(habits, currentState.searchQuery),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                _event.send(HabitListEvent.ShowSnackBar(e.message ?: "Error happened"))
            }
        }
    }

    private fun toggleHabit(habitId: Int) {
        viewModelScope.launch {
            val currentDate = LocalDate.now().toString()
            repository.toggleHabitCompletion(habitId, currentDate)
        }
    }

    private fun deleteHabit(habitId: Int) {
        viewModelScope.launch {
            repository.deleteHabit(habitId)
        }
    }

    private fun updateSearch(query: String) {
        _state.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredHabits = filterHabits(currentState.habits, query)
            )
        }
    }

    private fun filterHabits(habits: List<Habit>, query: String): List<Habit> {
        return if (query.isBlank()) {
            habits
        } else {
            habits.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    private fun navigateToDetail(habitId: Int) {
        viewModelScope.launch {
            _event.send(HabitListEvent.NavigateToDetail(habitId))
        }
    }

    private fun addNewHabit(name: String, emoji: String) {
        viewModelScope.launch {
            repository.addHabit(name = name, description = "", emoji = emoji)
        }
    }
}