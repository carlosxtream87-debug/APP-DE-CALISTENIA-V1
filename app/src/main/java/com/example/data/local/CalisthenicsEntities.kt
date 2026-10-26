package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_history")
data class WorkoutRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutName: String,
    val durationSeconds: Int,
    val completedExercisesCount: Int,
    val totalSetsCount: Int,
    val caloriesBurned: Int,
    val timestampMillis: Long = System.currentTimeMillis(),
    val notes: String = ""
)

@Entity(tableName = "skill_progress")
data class SkillProgressEntity(
    @PrimaryKey
    val skillId: String,
    val currentStepIndex: Int,
    val isMastered: Boolean = false,
    val personalBestRecord: String = "",
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "personal_records")
data class PersonalRecordEntity(
    @PrimaryKey
    val recordId: String,
    val value: String,
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_workouts")
data class CustomWorkoutEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val difficulty: String,
    val estimatedMinutes: Int,
    val equipmentCsv: String,
    val description: String,
    val exercisesJson: String,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)
