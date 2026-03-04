package com.example.habittrackerapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String = "",
    val iconEmoji: String = "✅",
    val createdAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false
)
