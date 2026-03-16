package com.example.habittrackerapp.domain.usecase

import com.example.habittrackerapp.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        val startStr = startDate.format(formatter)
        val endStr = endDate.format(formatter)

        return combine(
            habitRepository.getAllHabits(),
            habitRepository.getCompletionsInRange(startStr, endStr)
        )
        { habits, completions ->
            val totalHabits = habits.size
            if (totalHabits == 0) return@combine emptyList()
            val stats = mutableListOf<WeeklyStat>()

            for (i in 0..6) {
                val currentDate = startDate.plusDays(i.toLong())
                val dateStr = currentDate.format(formatter)
                val dayOfWeek = currentDate.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
                val completedCount = completions.count { it.completedDate == dateStr }
                val rate = completedCount.toFloat() / totalHabits.toFloat()

                stats.add(WeeklyStat(dayOfWeek, dateStr, rate))
            }
            stats
        }
    }
}