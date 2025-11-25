package com.example.workout.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkoutLog(workout: WorkoutLogEntity): Long
    
    @Insert
    suspend fun insertExerciseLog(exercise: ExerciseLogEntity)
    
    @Query("SELECT * FROM workout_logs ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<WorkoutLogEntity>>
    
    @Query("SELECT * FROM workout_logs WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): WorkoutLogEntity?
    
    @Query("SELECT * FROM exercise_logs WHERE workoutLogId = :workoutId")
    suspend fun getExercisesForWorkout(workoutId: Long): List<ExerciseLogEntity>
    
    @Query("SELECT COUNT(*) FROM workout_logs WHERE completed = 1")
    suspend fun getTotalCompletedWorkouts(): Int
    
    @Query("SELECT SUM(durationMinutes) FROM workout_logs WHERE completed = 1")
    suspend fun getTotalMinutesExercised(): Int
}
