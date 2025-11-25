package com.example.workout.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.workout.data.*

class WorkoutViewModel : ViewModel() {
    var currentPlan by mutableStateOf<WorkoutPlan?>(null)
        private set

    fun generatePlan(
        bmi: Double,
        age: Int,
        fitnessLevel: Difficulty,
        problemAreas: List<MuscleGroup>,
        timeAvailable: Int
    ) {
        currentPlan = WorkoutGenerator.generateWorkout(
            bmi = bmi,
            age = age,
            fitnessLevel = fitnessLevel,
            problemAreas = problemAreas,
            timeAvailableMin = timeAvailable
        )
    }
}
