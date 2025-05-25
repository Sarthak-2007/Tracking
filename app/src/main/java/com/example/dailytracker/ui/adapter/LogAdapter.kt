package com.example.dailytracker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytracker.R
import com.example.dailytracker.model.DailyLog

class LogAdapter(private val logList: MutableList<DailyLog>) :
    RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_log, parent, false)
        return LogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val currentLog = logList[position]
        holder.bind(currentLog)
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    inner class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logDateTextView: TextView = itemView.findViewById(R.id.logDateTextView)
        private val logSummaryTextView: TextView = itemView.findViewById(R.id.logSummaryTextView)

        fun bind(log: DailyLog) {
            logDateTextView.text = log.date
            val firstAchievement = log.top3Achievements.lines().firstOrNull { it.isNotBlank() } ?: "N/A"
            logSummaryTextView.text = "Sleep Quality: ${log.sleepQuality}/10, Achievements: $firstAchievement"
        }
    }

    // Optional: Method to update data if MainActivity wasn't directly modifying the list
    // fun updateData(newLogs: List<DailyLog>) {
    //     logList.clear()
    //     logList.addAll(newLogs)
    //     notifyDataSetChanged()
    // }
}
