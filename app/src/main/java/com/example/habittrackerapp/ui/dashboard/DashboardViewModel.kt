package com.example.habittrackerapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.domain.repository.HabitRepository
import com.example.habittrackerapp.domain.usecase.GetWeeklyStatsUseCase
import com.example.habittrackerapp.ui.dashboard.contract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: HabitRepository,
    private val weeklyUseCase: GetWeeklyStatsUseCase
) : ViewModel()
{
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            combine(
                repo.getAllHabits(),
                weeklyUseCase()
            ) { habits, weekly ->

                val total = habits.size
                val doneToday = habits.count { it.isCompletedToday }

                val rate = if (total == 0) 0f
                else doneToday.toFloat() / total

                DashboardState(
                    totalHabits = total,
                    completedToday = doneToday,
                    todayCompletionRate = rate,
                    weeklyStats = weekly,
                    longestStreak = calcStreak(weekly),
                    isLoading = false
                )
            }.collect {
                _state.value = it
            }
        }
    }

    private fun calcStreak(list: List<com.example.habittrackerapp.domain.usecase.WeeklyStat>): Int
    {
        var max = 0
        var cur = 0

        list.forEach {
            if (it.completionRate > 0f)
            {
                cur++
                if (cur > max) max = cur
            } else cur = 0
        }
        return max
    }
}