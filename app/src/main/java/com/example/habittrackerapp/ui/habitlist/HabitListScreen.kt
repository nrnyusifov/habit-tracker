package com.example.habittrackerapp.ui.habitlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.domain.model.Habit
import com.example.habittrackerapp.ui.components.HabitCard
import com.example.habittrackerapp.ui.habitlist.contract.HabitListIntent
import com.example.habittrackerapp.ui.habitlist.contract.HabitListState

@Composable
fun HabitListScreen(
    state: HabitListState,
    onIntent: (HabitListIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { onIntent(HabitListIntent.SearchQueryChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search...") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Habit Count: ${state.filteredHabits.size}",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.filteredHabits.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Habit Not Found")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.filteredHabits,
                    key = { it.id }
                ) { habit ->
                    HabitCard(
                        habit = habit,
                        onToggle = { onIntent(HabitListIntent.ToggleHabit(it)) },
                        onClick = { onIntent(HabitListIntent.SelectedHabit(it)) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitListScreenPreview() {
    HabitListScreen(
        state = HabitListState(
            filteredHabits = listOf(
                Habit(
                    id = 1,
                    name = "Reading",
                    description = "20 pages in a day",
                    iconEmoji = "📚",
                    createdAt = 1710068041000L,
                    isArchived = false,
                    isCompletedToday = false
                ),
                Habit(
                    id = 2,
                    name = "Sport",
                    description = "2 hour in a day",
                    iconEmoji = "🏋️",
                    createdAt = 1710068041000L,
                    isArchived = false,
                    isCompletedToday = true
                )
            ),
            isLoading = false
        ),
        onIntent = {}
    )
}