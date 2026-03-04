package com.example.habittrackerapp.data.mapper

import com.example.habittrackerapp.data.local.entity.HabitCompletionEntity
import com.example.habittrackerapp.data.local.entity.HabitEntity
import com.example.habittrackerapp.domain.model.Habit
import com.example.habittrackerapp.domain.model.HabitCompletion

fun HabitEntity.toDomain(isCompletedToday: Boolean): Habit {
    return Habit(
        id = this.id,
        name = this.name,
        description = this.description,
        iconEmoji = this.iconEmoji,
        createdAt = this.createdAt,
        isArchived = this.isArchived,
        isCompletedToday = isCompletedToday
    )
}

fun HabitCompletionEntity.toDomain(): HabitCompletion {
    return HabitCompletion(
        id = this.id,
        habitId = this.habitId,
        completedDate = this.completedDate,
        completedAt = this.completedAt
    )
}
