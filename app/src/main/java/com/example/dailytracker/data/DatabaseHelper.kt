package com.example.dailytracker.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dailytracker.model.DailyLog

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DailyTracker.db"
        const val TABLE_LOGS = "logs"

        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_WAKE_UP_TIME = "wake_up_time"
        const val COLUMN_SLEEP_TIME = "sleep_time"
        const val COLUMN_SLEEP_QUALITY = "sleep_quality"
        const val COLUMN_STUDY_HOURS = "study_hours"
        const val COLUMN_SUBJECTS_COVERED = "subjects_covered"
        const val COLUMN_PHYSICAL_TRAINING_COMPLETED = "physical_training_completed"
        const val COLUMN_MEDITATION_MINUTES = "meditation_minutes"
        const val COLUMN_NUTRITION_ADHERENCE = "nutrition_adherence"
        const val COLUMN_DIGITAL_DISCIPLINE_SCORE = "digital_discipline_score"
        const val COLUMN_ENERGY_LEVELS = "energy_levels"
        const val COLUMN_TOP_3_ACHIEVEMENTS = "top_3_achievements"
        const val COLUMN_TOMORROWS_PRIORITIES = "tomorrows_priorities"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_LOGS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_WAKE_UP_TIME TEXT NOT NULL,
                $COLUMN_SLEEP_TIME TEXT NOT NULL,
                $COLUMN_SLEEP_QUALITY INTEGER NOT NULL,
                $COLUMN_STUDY_HOURS TEXT NOT NULL,
                $COLUMN_SUBJECTS_COVERED TEXT NOT NULL,
                $COLUMN_PHYSICAL_TRAINING_COMPLETED TEXT NOT NULL,
                $COLUMN_MEDITATION_MINUTES INTEGER NOT NULL,
                $COLUMN_NUTRITION_ADHERENCE INTEGER NOT NULL,
                $COLUMN_DIGITAL_DISCIPLINE_SCORE INTEGER NOT NULL,
                $COLUMN_ENERGY_LEVELS TEXT NOT NULL,
                $COLUMN_TOP_3_ACHIEVEMENTS TEXT NOT NULL,
                $COLUMN_TOMORROWS_PRIORITIES TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LOGS")
        onCreate(db)
    }

    fun addLog(log: DailyLog): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, log.date)
            put(COLUMN_WAKE_UP_TIME, log.wakeUpTime)
            put(COLUMN_SLEEP_TIME, log.sleepTime)
            put(COLUMN_SLEEP_QUALITY, log.sleepQuality)
            put(COLUMN_STUDY_HOURS, log.studyHours)
            put(COLUMN_SUBJECTS_COVERED, log.subjectsCovered)
            put(COLUMN_PHYSICAL_TRAINING_COMPLETED, log.physicalTrainingCompleted)
            put(COLUMN_MEDITATION_MINUTES, log.meditationMinutes)
            put(COLUMN_NUTRITION_ADHERENCE, log.nutritionAdherence)
            put(COLUMN_DIGITAL_DISCIPLINE_SCORE, log.digitalDisciplineScore)
            put(COLUMN_ENERGY_LEVELS, log.energyLevels)
            put(COLUMN_TOP_3_ACHIEVEMENTS, log.top3Achievements)
            put(COLUMN_TOMORROWS_PRIORITIES, log.tomorrowsPriorities)
        }
        val id = db.insert(TABLE_LOGS, null, values)
        db.close()
        return id
    }

    fun getAllLogs(): List<DailyLog> {
        val logList = ArrayList<DailyLog>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_LOGS ORDER BY $COLUMN_DATE DESC"
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val log = DailyLog(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        wakeUpTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WAKE_UP_TIME)),
                        sleepTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SLEEP_TIME)),
                        sleepQuality = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SLEEP_QUALITY)),
                        studyHours = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_HOURS)),
                        subjectsCovered = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBJECTS_COVERED)),
                        physicalTrainingCompleted = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHYSICAL_TRAINING_COMPLETED)),
                        meditationMinutes = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MEDITATION_MINUTES)),
                        nutritionAdherence = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUTRITION_ADHERENCE)),
                        digitalDisciplineScore = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DIGITAL_DISCIPLINE_SCORE)),
                        energyLevels = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENERGY_LEVELS)),
                        top3Achievements = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOP_3_ACHIEVEMENTS)),
                        tomorrowsPriorities = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOMORROWS_PRIORITIES))
                    )
                    logList.add(log)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            // Handle exception, maybe log it
        } finally {
            cursor?.close()
            db.close()
        }
        return logList
    }
}
