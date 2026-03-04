package com.example.habittrackerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittrackerapp.data.local.dao.HabitCompletionDao
import com.example.habittrackerapp.data.local.dao.HabitDao
import com.example.habittrackerapp.data.local.entity.HabitCompletionEntity
import com.example.habittrackerapp.data.local.entity.HabitEntity

@Database(
    entities = [HabitEntity::class, HabitCompletionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitCompletionDao(): HabitCompletionDao
}