package com.example.jtrackera_1

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var distanceLeftText: TextView
    private lateinit var travelDetailsText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextStopBtn: Button

    private val stops = listOf(
        Stop("Neo City", "Start Location", 0, "0h 0m", "No"),
        Stop("Azure Bay", "Stop 1", 250, "2h 30m", "No"),
        Stop("Crimson Desert", "Stop 2", 300, "3h 15m", "Yes"),
        Stop("Emerald Highlands", "Stop 3", 400, "4h 10m", "No"),
        Stop("Sapphire Isles", "Stop 4", 200, "2h 05m", "Yes"),
        Stop("Golden Plateau", "Stop 5", 400, "4h 30m", "No"),
        Stop("Obsidian Peaks", "End Location", 0, "0h 0m", "Yes")
    )

    private var visitedStops = mutableSetOf<String>()
    private var currentStopIndex = 0
    private lateinit var stopAdapter: StopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        distanceLeftText = findViewById(R.id.distanceLeftText)
        travelDetailsText = findViewById(R.id.travelDetailsText)
        progressBar = findViewById(R.id.progressBar)
        nextStopBtn = findViewById(R.id.nextStopBtn)

        stopAdapter = StopAdapter(this, stops, visitedStops)
        listView.adapter = stopAdapter

        updateUI()

        nextStopBtn.setOnClickListener {
            if (currentStopIndex < stops.size - 1) {
                visitedStops.add(stops[currentStopIndex].name)
                currentStopIndex++
                updateUI()
            }
        }
    }

    private fun updateUI() {
        val currentStop = stops[currentStopIndex]

        // Update progress bar
        val progress = ((currentStopIndex.toFloat() / (stops.size - 1)) * 100).toInt()
        progressBar.progress = progress

        // Update distance & travel info
        distanceLeftText.text = "Distance Left: ${currentStop.distance} km"
        travelDetailsText.text = "Total Distance Covered: ${
            stops.subList(0, currentStopIndex + 1).sumOf { it.distance }
        } km\nTotal Time Taken: ${
            stops.subList(0, currentStopIndex + 1).sumOf { it.time.split("h")[0].toInt() }
        }h ${
            stops.subList(0, currentStopIndex + 1).sumOf { it.time.split(" ")[1].replace("m", "").toInt() }
        }m\nVisa Required Here: ${if (currentStop.visaRequired == "Yes") "Visa Required." else "No Visa Required."}"

        // Refresh list view
        stopAdapter.notifyDataSetChanged()
    }
}

data class Stop(val name: String, val description: String, val distance: Int, val time: String, val visaRequired: String)

