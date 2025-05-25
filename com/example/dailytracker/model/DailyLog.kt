package com.example.dailytracker.model

data class DailyLog(
    val id: Long = 0,
    val date: String,
    val wakeUpTime: String,
    val sleepTime: String,
    val sleepQuality: Int,
    val studyHours: String,
    val subjectsCovered: String,
    val physicalTrainingCompleted: String,
    val meditationMinutes: Int,
    val nutritionAdherence: Int,
    val digitalDisciplineScore: Int,
    val energyLevels: String,
    val top3Achievements: String,
    val tomorrowsPriorities: String
)
