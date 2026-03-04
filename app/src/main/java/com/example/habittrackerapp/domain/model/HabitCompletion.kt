package com.example.habittrackerapp.domain.model

data class HabitCompletion(
    val id: Int,
    val habitId: Int,
    val completedDate: String,
    val completedAt: Long
)