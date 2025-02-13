package com.example.journeytrackerc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.journeytrackerc.ui.theme.JourneyTrackerCTheme

data class JourneyStop(
    val location: String,
    val distance: Int?,
    val travelTime: Int?,
    val visaRequired: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JourneyTrackerCTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val journeyStops = listOf(
        JourneyStop("Start Location: Neo City", null, null, "Visa: Not required."),
        JourneyStop("Stop 1: Azure Bay", 150, 128, "Visa Required: No."),
        JourneyStop("Stop 2: Crimson Desert", 300, 252, "Visa Required: No."),
        JourneyStop("Stop 3: Emerald Highlands", 200, 180, "Visa Required: No."),
        JourneyStop("Stop 4: Sapphire Isles", 500, 98, "Visa Required: Yes."),
        JourneyStop("Stop 5: Golden Plateau", 400, 116, "Visa Required: Yes."),
        JourneyStop("End Location: Obsidian Peaks", 250, 177, "Visa Required: No.")
    )

    var currentStopIndex by remember { mutableStateOf(0) }
    var isInMiles by remember { mutableStateOf(false) }
    val totalDistance = journeyStops.mapNotNull { it.distance }.sum()
    val coveredDistance = journeyStops.take(currentStopIndex + 1).mapNotNull { it.distance }.sum()
    val remainingDistance = totalDistance - coveredDistance
    val isFinalDestination = currentStopIndex == journeyStops.lastIndex

    val totalTimeTakenMinutes = journeyStops
        .take(currentStopIndex + 1)
        .mapNotNull { it.travelTime }
        .sum()
    val hours = totalTimeTakenMinutes / 60
    val minutes = totalTimeTakenMinutes % 60
    val formattedTotalTimeTaken = "${hours}h ${minutes}m"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Journey Tracker",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hello User,",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        JourneyList(
            journeyStops = journeyStops,
            currentStopIndex = currentStopIndex,
            isInMiles = isInMiles,
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFC2E9F2))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = if (totalDistance > 0) coveredDistance.toFloat() / totalDistance else 0f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Total Distance Covered: ${if (isInMiles) "%.1f miles".format(coveredDistance * 0.621371) else "$coveredDistance km"}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Distance Left: ${if (isInMiles) "%.1f miles".format(remainingDistance * 0.621371) else "$remainingDistance km"}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Total Time Taken: $formattedTotalTimeTaken",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Visa Required Here: ${journeyStops[currentStopIndex].visaRequired}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
        if (isFinalDestination) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Final Destination Reached",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                color = Color(0xFFB63C3A)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (!isFinalDestination) {
                        currentStopIndex++
                    }
                }
            ) {
                Text("Next Stop")
            }
            Button(
                onClick = {
                    isInMiles = !isInMiles
                }
            ) {
                Text("Convert Units")
            }
        }
    }
}

@Composable
fun JourneyList(
    journeyStops: List<JourneyStop>,
    currentStopIndex: Int,
    isInMiles: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(journeyStops) { stop ->
            JourneyItem(
                stop = stop,
                isHighlighted = journeyStops.indexOf(stop) <= currentStopIndex,
                isInMiles = isInMiles
            )
        }
    }
}

@Composable
fun JourneyItem(stop: JourneyStop, isHighlighted: Boolean, isInMiles: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (isHighlighted) Color(0xFFEDF2C2) else Color.Transparent)
    ) {
        Text(
            text = stop.location,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )
        stop.distance?.let { distanceKm ->
            val distance = if (isInMiles) {
                "%.1f miles".format(distanceKm * 0.621371)
            } else {
                "$distanceKm km"
            }
            Text(
                text = "Distance: $distance",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
        stop.travelTime?.let { travelTime ->
            val travelHours = travelTime / 60
            val travelMinutes = travelTime % 60
            Text(
                text = "Travel Time: ${travelHours}h ${travelMinutes}m",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = stop.visaRequired,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    JourneyTrackerCTheme {
        MainScreen()
    }
}






