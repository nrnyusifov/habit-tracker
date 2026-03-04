package com.example.habittrackerapp.domain.repository

import com.example.habittrackerapp.domain.model.Habit
import com.example.habittrackerapp.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    fun getHabitById(habitId: Int): Flow<Habit?>
    fun getCompletionsForHabit(habitId: Int): Flow<List<HabitCompletion>>
    fun getCompletionsInRange(start: String, end: String): Flow<List<HabitCompletion>>
    suspend fun addHabit(name: String, description: String, emoji: String)
    suspend fun toggleHabitCompletion(habitId: Int, date: String)
    suspend fun deleteHabit(habitId: Int)
}