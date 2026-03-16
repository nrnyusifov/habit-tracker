package com.example.habittrackerapp.domain.usecase

import com.example.habittrackerapp.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

data class WeeklyStat(
    val dayOfWeek: String,
    val date: String,
    val completionRate: Float
)

class GetWeeklyStatsUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    operator fun invoke(): Flow<List<WeeklyStat>> {
        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(6)

        val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val dayNameFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)

        val startStr = startDate.format(dateFormatter)
        val endStr = endDate.format(dateFormatter)

        return combine(
            habitRepository.getAllHabits(),
            habitRepository.getCompletionsInRange(startStr, endStr)
        ) { habits, completions ->
            val totalHabits = habits.size
            val weeklyStats = mutableListOf<WeeklyStat>()

            for (dayIndex in 0..6) {
                val currentDate = startDate.plusDays(dayIndex.toLong())

                val dateStr = currentDate.format(dateFormatter)
                val dayOfWeekStr = currentDate.format(dayNameFormatter)

                val rate = if (totalHabits == 0) {
                    0f
                } else {
                    val completedCount = completions.count { it.completedDate == dateStr }
                    completedCount.toFloat() / totalHabits.toFloat()
                }

                weeklyStats.add(
                    WeeklyStat(
                        dayOfWeek = dayOfWeekStr,
                        date = dateStr,
                        completionRate = rate
                    )
                )
            }

            weeklyStats
        }
    }
}