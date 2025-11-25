package com.example.workout.data

import kotlin.random.Random

object WorkoutGenerator {

    fun generateWorkout(
        bmi: Double,
        age: Int,
        fitnessLevel: Difficulty,
        problemAreas: List<MuscleGroup>,
        timeAvailableMin: Int,
        equipment: List<Equipment> = listOf(Equipment.NONE) // Default to none for now
    ): WorkoutPlan {
        val exercises = mutableListOf<WorkoutExercise>()
        val targetExercises = ExerciseRepository.exercises.filter { exercise ->
            // Filter by equipment (if exercise needs none, it's always good. If it needs something, we must have it)
            val hasEquipment = exercise.equipment.all { it == Equipment.NONE || equipment.contains(it) }
            // Filter by difficulty (roughly)
            val difficultyMatch = when (fitnessLevel) {
                Difficulty.BEGINNER -> exercise.difficulty == Difficulty.BEGINNER
                Difficulty.INTERMEDIATE -> exercise.difficulty != Difficulty.ADVANCED
                Difficulty.ADVANCED -> true
            }
            
            // Age filter: Seniors (>60) avoid HIIT unless Advanced
            val ageSafe = if (age > 60) exercise.category != ExerciseCategory.HIIT else true
            
            hasEquipment && difficultyMatch && ageSafe
        }

        // Warmup (Fixed for now)
        exercises.add(WorkoutExercise(ExerciseRepository.exercises.find { it.name == "Jumping Jacks" }!!, durationSeconds = 60))
        exercises.add(WorkoutExercise(ExerciseRepository.exercises.find { it.name == "Arm Circles" }!!, durationSeconds = 30))

        // Main Workout
        // Strategy: Pick exercises targeting problem areas first, then fill with full body
        val problemAreaExercises = targetExercises.filter { ex ->
            ex.muscleGroups.any { problemAreas.contains(it) }
        }.shuffled()

        val otherExercises = targetExercises.filter { ex ->
            !ex.muscleGroups.any { problemAreas.contains(it) }
        }.shuffled()

        // Calculate how many exercises we can fit
        // Approx 2-3 mins per exercise (including rest)
        val availableTimeForMain = timeAvailableMin - 5 // 5 min warmup
        val numExercises = (availableTimeForMain / 3).coerceAtLeast(3)

        var count = 0
        val selected = mutableListOf<Exercise>()

        // Add problem area exercises
        for (ex in problemAreaExercises) {
            if (count >= numExercises) break
            if (!selected.contains(ex)) {
                selected.add(ex)
                exercises.add(WorkoutExercise(ex))
                count++
            }
        }

        // Fill rest
        for (ex in otherExercises) {
            if (count >= numExercises) break
            if (!selected.contains(ex)) {
                selected.add(ex)
                exercises.add(WorkoutExercise(ex))
                count++
            }
        }

        // Cooldown (Fixed)
        // (In a real app, we'd have specific stretches)

        return WorkoutPlan(
            id = "wp_${System.currentTimeMillis()}",
            name = "Custom ${fitnessLevel.name.lowercase().capitalize()} Workout",
            description = "A personalized routine focusing on ${problemAreas.joinToString { it.name.lowercase() }}.",
            difficulty = fitnessLevel,
            targetArea = problemAreas.joinToString { it.name },
            exercises = exercises,
            totalDurationMin = timeAvailableMin,
            estimatedCalories = timeAvailableMin * 8 // Approx
        )
    }
}

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
