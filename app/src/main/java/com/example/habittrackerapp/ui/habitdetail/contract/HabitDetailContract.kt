package com.example.habittrackerapp.ui.habitdetail.contract

import java.time.LocalDate
import java.time.YearMonth

data class HabitDetailState(
    val habitId: Int = 0,
    val habitName: String = "",
    val habitEmoji: String = "",
    val createdAt: Long = 0L,
    val selectedMonth: YearMonth = YearMonth.now(),
    val completedDaysInMonth: Set<LocalDate> = emptySet(),
    val streakCount: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

sealed interface HabitDetailIntent {
    data object LoadHabit : HabitDetailIntent
    data object GoToPreviousMonth : HabitDetailIntent
    data object GoToNextMonth : HabitDetailIntent
    data class ToggleDay(val date: LocalDate) : HabitDetailIntent
}
