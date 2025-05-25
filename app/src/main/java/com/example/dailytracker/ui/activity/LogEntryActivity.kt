package com.example.dailytracker.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dailytracker.R
import com.example.dailytracker.data.DatabaseHelper
import com.example.dailytracker.model.DailyLog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LogEntryActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    private lateinit var logDateEditText: TextInputEditText
    private lateinit var wakeUpTimeEditText: TextInputEditText
    private lateinit var sleepTimeEditText: TextInputEditText
    private lateinit var sleepQualityRatingBar: RatingBar
    private lateinit var studyHoursEditText: TextInputEditText
    private lateinit var subjectsCoveredEditText: TextInputEditText
    private lateinit var physicalTrainingEditText: TextInputEditText
    private lateinit var meditationMinutesEditText: TextInputEditText
    private lateinit var nutritionAdherenceRatingBar: RatingBar
    private lateinit var digitalDisciplineRatingBar: RatingBar
    private lateinit var energyLevelsEditText: TextInputEditText
    private lateinit var top3AchievementsEditText: TextInputEditText
    private lateinit var tomorrowsPrioritiesEditText: TextInputEditText
    private lateinit var saveLogButton: MaterialButton

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_entry)

        dbHelper = DatabaseHelper(this)

        logDateEditText = findViewById(R.id.logDateEditText)
        wakeUpTimeEditText = findViewById(R.id.wakeUpTimeEditText)
        sleepTimeEditText = findViewById(R.id.sleepTimeEditText)
        sleepQualityRatingBar = findViewById(R.id.sleepQualityRatingBar)
        studyHoursEditText = findViewById(R.id.studyHoursEditText)
        subjectsCoveredEditText = findViewById(R.id.subjectsCoveredEditText)
        physicalTrainingEditText = findViewById(R.id.physicalTrainingEditText)
        meditationMinutesEditText = findViewById(R.id.meditationMinutesEditText)
        nutritionAdherenceRatingBar = findViewById(R.id.nutritionAdherenceRatingBar)
        digitalDisciplineRatingBar = findViewById(R.id.digitalDisciplineRatingBar)
        energyLevelsEditText = findViewById(R.id.energyLevelsEditText)
        top3AchievementsEditText = findViewById(R.id.top3AchievementsEditText)
        tomorrowsPrioritiesEditText = findViewById(R.id.tomorrowsPrioritiesEditText)
        saveLogButton = findViewById(R.id.saveLogButton)

        // Set current date as default
        updateDateInView(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        logDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
        // Make sure to set focusable to false in XML for these EditTexts if they are only for dialogs
        wakeUpTimeEditText.setOnClickListener {
            showTimePickerDialog(wakeUpTimeEditText)
        }
        sleepTimeEditText.setOnClickListener {
            showTimePickerDialog(sleepTimeEditText)
        }

        saveLogButton.setOnClickListener {
            saveLogEntry()
        }
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            updateDateInView(selectedYear, selectedMonth, selectedDay)
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun updateDateInView(year: Int, month: Int, dayOfMonth: Int) {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(year, month, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        logDateEditText.setText(sdf.format(selectedCalendar.time))
    }


    private fun showTimePickerDialog(editText: TextInputEditText) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format(Locale.US, "%02d:%02d", selectedHour, selectedMinute)
            editText.setText(formattedTime)
        }, hour, minute, true) // true for 24-hour view
        timePickerDialog.show()
    }

    private fun saveLogEntry() {
        val date = logDateEditText.text.toString().trim()
        val wakeUpTime = wakeUpTimeEditText.text.toString().trim()
        val sleepTime = sleepTimeEditText.text.toString().trim()
        val sleepQuality = sleepQualityRatingBar.rating.toInt()
        val studyHours = studyHoursEditText.text.toString().trim()
        val subjectsCovered = subjectsCoveredEditText.text.toString().trim()
        val physicalTrainingCompleted = physicalTrainingEditText.text.toString().trim()
        val meditationMinutes = meditationMinutesEditText.text.toString().trim().toIntOrNull() ?: 0
        val nutritionAdherence = nutritionAdherenceRatingBar.rating.toInt()
        val digitalDisciplineScore = digitalDisciplineRatingBar.rating.toInt()
        val energyLevels = energyLevelsEditText.text.toString().trim()
        val top3Achievements = top3AchievementsEditText.text.toString().trim()
        val tomorrowsPriorities = tomorrowsPrioritiesEditText.text.toString().trim()

        if (date.isEmpty() || wakeUpTime.isEmpty() || sleepTime.isEmpty()) {
            Toast.makeText(this, "Date, Wake Up Time, and Sleep Time cannot be empty.", Toast.LENGTH_LONG).show()
            return
        }
        if (sleepQuality == 0 || nutritionAdherence == 0 || digitalDisciplineScore == 0) {
            Toast.makeText(this, "Sleep Quality, Nutrition Adherence, and Digital Discipline Score must be rated (greater than 0).", Toast.LENGTH_LONG).show()
            return
        }
        // Additional validation for other fields can be added here if necessary

        val newLog = DailyLog(
            // id is auto-generated by DB, so we pass the default value or 0
            date = date,
            wakeUpTime = wakeUpTime,
            sleepTime = sleepTime,
            sleepQuality = sleepQuality,
            studyHours = studyHours,
            subjectsCovered = subjectsCovered,
            physicalTrainingCompleted = physicalTrainingCompleted,
            meditationMinutes = meditationMinutes,
            nutritionAdherence = nutritionAdherence,
            digitalDisciplineScore = digitalDisciplineScore,
            energyLevels = energyLevels,
            top3Achievements = top3Achievements,
            tomorrowsPriorities = tomorrowsPriorities
        )

        val resultId = dbHelper.addLog(newLog)

        if (resultId != -1L) {
            Toast.makeText(this, "Log saved successfully! ID: $resultId", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Error saving log.", Toast.LENGTH_SHORT).show()
        }
    }
}
