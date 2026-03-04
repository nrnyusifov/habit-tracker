package com.example.habittrackerapp.domain.model

data class Habit(
    val id: Int,
    val name: String,
    val description: String,
    val iconEmoji: String,
    val createdAt: Long,
    val isArchived: Boolean,
    val isCompletedToday: Boolean
)