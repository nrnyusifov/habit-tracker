package com.example.habittrackerapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.domain.usecase.WeeklyStat

@Composable
fun WeeklyChart(
    weeklyStats: List<WeeklyStat>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        weeklyStats.forEach { stat ->
            ChartBar(
                stat = stat,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ChartBar(
    stat: WeeklyStat,
    modifier: Modifier = Modifier
) {
    val animatedHeight by animateFloatAsState(
        targetValue = stat.completionRate,
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )

    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        val barColor = MaterialTheme.colorScheme.primary
        val trackColor = MaterialTheme.colorScheme.surfaceVariant

        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.5f)
        ) {
            val width = size.width
            val height = size.height

            drawRoundRect(
                color = trackColor,
                topLeft = Offset(0f, 0f),
                size = Size(width, height),
                cornerRadius = CornerRadius(width / 2, width / 2)
            )

            val barHeight = height * animatedHeight
            drawRoundRect(
                color = barColor,
                topLeft = Offset(0f, height - barHeight),
                size = Size(width, barHeight),
                cornerRadius = CornerRadius(width / 2, width / 2)
            )
        }

        Text(
            text = stat.dayOfWeek,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeeklyChartPreview() {
    MaterialTheme {
        WeeklyChart(
            weeklyStats = listOf(
                WeeklyStat("Mon", "2026-03-09", 0.5f),
                WeeklyStat("Tue", "2026-03-10", 0.8f),
                WeeklyStat("Wed", "2026-03-11", 1.0f),
                WeeklyStat("Thu", "2026-03-12", 0.4f),
                WeeklyStat("Fri", "2026-03-13", 0.6f),
                WeeklyStat("Sat", "2026-03-14", 0.2f),
                WeeklyStat("Sun", "2026-03-15", 0.9f)
            )
        )
    }
}