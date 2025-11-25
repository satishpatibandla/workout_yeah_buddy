package com.example.workout.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workout_logs")
data class WorkoutLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutPlanId: String,
    val workoutName: String,
    val date: Long,
    val startTime: Long,
    val endTime: Long?,
    val durationMinutes: Int,
    val completed: Boolean,
    val notes: String?
)

@Entity(tableName = "exercise_logs")
data class ExerciseLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutLogId: Long,
    val exerciseId: String,
    val exerciseName: String,
    val setsCompleted: Int,
    val repsCompleted: String, // Can be "12" or "30 seconds"
    val weightUsed: Double?,
    val difficultyRating: Int, // 1-5
    val notes: String?,
    val timestamp: Long
)
