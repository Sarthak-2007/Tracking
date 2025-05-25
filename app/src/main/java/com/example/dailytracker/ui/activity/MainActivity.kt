package com.example.dailytracker.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytracker.R
import com.example.dailytracker.data.DatabaseHelper
import com.example.dailytracker.model.DailyLog
import com.example.dailytracker.ui.adapter.LogAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var logsRecyclerView: RecyclerView
    private lateinit var addLogFab: FloatingActionButton
    private lateinit var logAdapter: LogAdapter
    private var logList: MutableList<DailyLog> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        logsRecyclerView = findViewById(R.id.logsRecyclerView)
        addLogFab = findViewById(R.id.addLogFab)

        logsRecyclerView.layoutManager = LinearLayoutManager(this)
        logAdapter = LogAdapter(logList) // Initialize with the modifiable list
        logsRecyclerView.adapter = logAdapter

        addLogFab.setOnClickListener {
            val intent = Intent(this, LogEntryActivity::class.java)
            startActivityForResult(intent, LOG_ENTRY_REQUEST_CODE)
        }

        loadLogs()
    }

    private fun loadLogs() {
        val fetchedLogs = dbHelper.getAllLogs()
        logList.clear()
        logList.addAll(fetchedLogs)
        logAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOG_ENTRY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadLogs()
        }
    }

    companion object {
        private const val LOG_ENTRY_REQUEST_CODE = 1
    }
}
