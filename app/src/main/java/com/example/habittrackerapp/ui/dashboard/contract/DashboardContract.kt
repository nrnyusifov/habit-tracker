package com.example.habittrackerapp.ui.dashboard.contract

import com.example.habittrackerapp.domain.usecase.WeeklyStat

data class DashboardState(
    val totalHabits: Int = 0,
    val completedToday: Int = 0,
    val todayCompletionRate: Float = 0f,
    val longestStreak: Int = 0,
    val weeklyStats: List<WeeklyStat> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface DashboardIntent {
    data object LoadDashboardData : DashboardIntent
}

sealed interface DashboardEvent