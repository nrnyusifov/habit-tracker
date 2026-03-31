package com.example.habittrackerapp.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.ui.components.WeeklyChart
import com.example.habittrackerapp.ui.dashboard.contract.DashboardState

@Composable
fun DashboardScreen(
    state: DashboardState
) {
    if (state.isLoading) {
        CircularProgressIndicator()
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Dashboard")
        Text("Total habits: ${state.totalHabits}")
        Text("Completed today: ${state.completedToday}")
        Text("Today rate: ${(state.todayCompletionRate * 100).toInt()}%")
        Text("Longest streak: ${state.longestStreak}")

        Spacer(Modifier.height(20.dp))
        WeeklyChart(weeklyStats = state.weeklyStats)
    }
}