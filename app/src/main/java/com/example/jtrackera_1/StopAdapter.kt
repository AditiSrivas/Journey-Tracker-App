package com.example.jtrackera_1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.BaseAdapter

class StopAdapter(private val context: Context, private val stops: List<Stop>, private val visitedStops: Set<String>) : BaseAdapter() {

    override fun getCount(): Int = stops.size
    override fun getItem(position: Int): Any = stops[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_stop, parent, false)

        val stopNameText: TextView = view.findViewById(R.id.stopNameText)
        val stopDetailsText: TextView = view.findViewById(R.id.stopDetailsText)

        val stop = stops[position]

        stopNameText.text = "${stop.description}: ${stop.name}"
        stopDetailsText.text = "Distance: ${stop.distance} km | Time: ${stop.time} | Visa: ${stop.visaRequired}"

        // Highlight visited stops
        if (visitedStops.contains(stop.name)) {
            view.setBackgroundColor(Color.parseColor("#FFFBB5")) // Light yellow for visited stops
        } else {
            view.setBackgroundColor(Color.WHITE)
        }

        return view
    }
}