package com.example.habittrackerapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.domain.model.Habit

@Composable
fun HabitCard(
    habit: Habit,
    onToggle: (Int) -> Unit,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = { onClick(habit.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habit.iconEmoji,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleMedium
                )
                if (habit.description.isNotEmpty()) {
                    Text(
                        text = habit.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Checkbox(
                checked = habit.isCompletedToday,
                onCheckedChange = { onToggle(habit.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitCardPreview() {
    HabitCard(
        habit = Habit(
            id = 1,
            name = "Drink water",
            description = "2 liters per day",
            iconEmoji = "💧",
            createdAt = 1710068041000L,
            isArchived = false,
            isCompletedToday = false
        ),
        onToggle = {},
        onClick = {}
    )
}