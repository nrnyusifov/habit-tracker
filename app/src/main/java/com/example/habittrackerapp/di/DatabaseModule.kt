package com.example.habittrackerapp.di

import android.content.Context
import androidx.room.Room
import com.example.habittrackerapp.data.local.AppDatabase
import com.example.habittrackerapp.data.local.dao.HabitCompletionDao
import com.example.habittrackerapp.data.local.dao.HabitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "habit-db").build()
    }

    @Provides
    fun provideHabitDao(db: AppDatabase): HabitDao = db.habitDao()

    @Provides
    fun provideCompletionDao(db: AppDatabase): HabitCompletionDao = db.habitCompletionDao()
}