package com.example.habittrackerapp.di

import com.example.habittrackerapp.data.repository.HabitRepositoryImpl
import com.example.habittrackerapp.domain.repository.HabitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(impl: HabitRepositoryImpl): HabitRepository
}