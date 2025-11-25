package com.example.workout.data

data class Exercise(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Difficulty,
    val equipment: List<Equipment>,
    val muscleGroups: List<MuscleGroup>,
    val category: ExerciseCategory,
    val animationUrl: String? = null, // URL or local resource name
    val instructions: List<String>,
    val tips: List<String> = emptyList(),
    val durationSeconds: Int? = null, // For time-based
    val reps: String? = null // Recommended reps (e.g., "10-12")
)

enum class Difficulty {
    BEGINNER, INTERMEDIATE, ADVANCED
}

enum class Equipment {
    NONE, DUMBBELLS, BARBELL, RESISTANCE_BANDS, KETTLEBELL, GYM_MACHINE, MAT
}

enum class MuscleGroup {
    ABS, CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, LEGS, GLUTES, CALVES, FULL_BODY, CARDIO
}

enum class ExerciseCategory {
    STRENGTH, CARDIO, FLEXIBILITY, BALANCE, HIIT
}

data class WorkoutPlan(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Difficulty,
    val targetArea: String,
    val exercises: List<WorkoutExercise>,
    val totalDurationMin: Int,
    val estimatedCalories: Int
)

data class WorkoutExercise(
    val exercise: Exercise,
    val sets: Int = 3,
    val reps: String = "10-12",
    val durationSeconds: Int? = null,
    val restSeconds: Int = 30
)
