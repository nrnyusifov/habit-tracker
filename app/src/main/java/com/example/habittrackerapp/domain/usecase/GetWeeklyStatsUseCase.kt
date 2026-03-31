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
    private val repository: HabitRepository
) {
    operator fun invoke(): Flow<List<WeeklyStat>> {

        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(6)

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)

        return combine(
            repository.getAllHabits(),
            repository.getCompletionsInRange(
                startDate.format(formatter),
                endDate.format(formatter)
            )
        ) { habits, completions ->

            val total = habits.size

            (0..6).map { i ->
                val date = startDate.plusDays(i.toLong())
                val dateStr = date.format(formatter)

                val completed = completions
                    .filter { it.completedDate == dateStr }
                    .map { it.habitId }
                    .distinct()
                    .size

                val rate = if (total == 0) 0f
                else completed.toFloat() / total

                WeeklyStat(
                    dayOfWeek = date.format(dayFormatter),
                    date = dateStr,
                    completionRate = rate
                )
            }
        }
    }
}