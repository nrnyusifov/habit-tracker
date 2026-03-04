package com.example.habittrackerapp.data.repository

import com.example.habittrackerapp.data.local.dao.HabitCompletionDao
import com.example.habittrackerapp.data.local.dao.HabitDao
import com.example.habittrackerapp.data.local.entity.HabitCompletionEntity
import com.example.habittrackerapp.data.local.entity.HabitEntity
import com.example.habittrackerapp.data.mapper.toDomain
import com.example.habittrackerapp.domain.model.Habit
import com.example.habittrackerapp.domain.model.HabitCompletion
import com.example.habittrackerapp.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitCompletionDao: HabitCompletionDao
) : HabitRepository
{
    override suspend fun addHabit(name: String, description: String, emoji: String)
    {
        val newHabit = HabitEntity(
            name = name,
            description = description,
            iconEmoji = emoji,
            createdAt = System.currentTimeMillis(),
            isArchived = false
        )
        habitDao.insertHabit(newHabit)
    }

    override suspend fun deleteHabit(habitId: Int)
    {
        habitDao.deleteHabitById(habitId)
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllActiveHabits().map { entities ->
            val today = LocalDate.now()
            val zone = java.time.ZoneId.systemDefault()
            val startOfDay = today.atStartOfDay(zone).toInstant().toEpochMilli()
            val endOfDay = today.atTime(java.time.LocalTime.MAX).atZone(zone).toInstant().toEpochMilli()

            entities.map { entity ->
                val count = habitCompletionDao.getCompletionCountInRange(
                    habitId = entity.id,
                    start = startOfDay,
                    end = endOfDay
                )
                entity.toDomain(isCompletedToday = count > 0)
            }
        }
    }

    override fun getHabitById(habitId: Int): Flow<Habit?> {
        val today = LocalDate.now().toString()

        return habitDao.getHabitWithCompletions(habitId).map { resultMap ->
            val entity = resultMap.keys.firstOrNull()
            val completions = resultMap.values.firstOrNull() ?: emptyList()
            entity?.let {
                Habit(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    iconEmoji = it.iconEmoji,
                    createdAt = it.createdAt,
                    isArchived = it.isArchived,
                    isCompletedToday = completions.any { c -> c.completedDate == today }
                )
            }
        }
    }

    override fun getCompletionsForHabit(habitId: Int): Flow<List<HabitCompletion>> {
        return habitCompletionDao.getCompletionsForHabit(habitId).map{
            entities -> entities.map {it.toDomain()}
        }
    }

    override fun getCompletionsInRange(start: String, end: String): Flow<List<HabitCompletion>> {
        return habitCompletionDao.getCompletionsInRange(start, end).map {
            entities -> entities.map {it.toDomain() }
        }
    }

    override suspend fun toggleHabitCompletion(habitId: Int, date: String) {
        val isCompleted = habitCompletionDao.isHabitCompletedOnDate(habitId, date)
        if (isCompleted)  habitCompletionDao.removeCompletion(habitId, date)
        else {
            habitCompletionDao.insertCompletion(
                HabitCompletionEntity(
                    habitId = habitId,
                    completedDate = date
                )
            )
        }
    }
}