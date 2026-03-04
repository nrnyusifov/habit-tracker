package com.example.habittrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habittrackerapp.data.local.entity.HabitCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCompletionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompletion(completion: HabitCompletionEntity)

    @Query("DELETE FROM habit_completions WHERE habitId = :habitId AND completedDate = :completedDate")
    suspend fun removeCompletion(habitId: Int, completedDate: String)

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY completedAt DESC")
    fun getCompletionsForHabit(habitId: Int): Flow<List<HabitCompletionEntity>>

    @Query("SELECT * FROM habit_completions WHERE completedDate BETWEEN :startDate AND :endDate")
    fun getCompletionsInRange(startDate: String, endDate: String): Flow<List<HabitCompletionEntity>>

    @Query("SELECT COUNT(*) FROM habit_completions WHERE habitId = :habitId AND completedAt BETWEEN :start AND :end")
    suspend fun getCompletionCountInRange(habitId: Int, start: Long, end: Long): Int

    @Query("SELECT EXISTS(SELECT 1 FROM habit_completions WHERE habitId = :habitId AND completedDate = :date)")
    suspend fun isHabitCompletedOnDate(habitId: Int, date: String): Boolean
}