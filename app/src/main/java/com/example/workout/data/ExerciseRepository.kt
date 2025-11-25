package com.example.workout.data

object ExerciseRepository {
    val exercises = listOf(
        // CARDIO
        Exercise(
            id = "c1", name = "Jumping Jacks", description = "A classic cardio exercise to warm up.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.FULL_BODY, MuscleGroup.CARDIO), category = ExerciseCategory.CARDIO,
            instructions = listOf("Stand upright with legs together, arms at sides.", "Jump legs out and raise arms overhead.", "Jump back to starting position."),
            durationSeconds = 60
        ),
        Exercise(
            id = "c2", name = "High Knees", description = "Run in place bringing knees high.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.CARDIO), category = ExerciseCategory.CARDIO,
            instructions = listOf("Stand tall.", "Run in place, lifting knees to waist height.", "Pump arms."),
            durationSeconds = 45
        ),
        Exercise(
            id = "c3", name = "Burpees", description = "Full body explosive movement.",
            difficulty = Difficulty.ADVANCED, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.FULL_BODY, MuscleGroup.CHEST, MuscleGroup.LEGS), category = ExerciseCategory.HIIT,
            instructions = listOf("Squat down.", "Kick feet back to plank.", "Do a push-up.", "Jump feet forward.", "Jump up."),
            reps = "10-15"
        ),
        Exercise(
            id = "c4", name = "Mountain Climbers", description = "Core and cardio in plank position.",
            difficulty = Difficulty.INTERMEDIATE, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.ABS, MuscleGroup.SHOULDERS, MuscleGroup.CARDIO), category = ExerciseCategory.HIIT,
            instructions = listOf("Start in plank.", "Drive one knee to chest.", "Switch legs quickly."),
            durationSeconds = 45
        ),

        // ABS / CORE
        Exercise(
            id = "a1", name = "Plank", description = "Isometric core hold.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.ABS, MuscleGroup.SHOULDERS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Forearms on ground.", "Body in straight line.", "Hold."),
            durationSeconds = 60
        ),
        Exercise(
            id = "a2", name = "Bicycle Crunches", description = "Target obliques and abs.",
            difficulty = Difficulty.INTERMEDIATE, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.ABS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Lie on back.", "Hands behind head.", "Touch elbow to opposite knee.", "Alternate."),
            reps = "20"
        ),
        Exercise(
            id = "a3", name = "Russian Twists", description = "Rotational core exercise.",
            difficulty = Difficulty.INTERMEDIATE, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.ABS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Sit with feet off ground.", "Twist torso side to side.", "Touch ground on each side."),
            reps = "20"
        ),
        Exercise(
            id = "a4", name = "Leg Raises", description = "Lower abs focus.",
            difficulty = Difficulty.INTERMEDIATE, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.ABS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Lie flat.", "Lift legs straight up.", "Lower slowly without touching ground."),
            reps = "12-15"
        ),

        // LEGS
        Exercise(
            id = "l1", name = "Squats", description = "Fundamental leg exercise.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.GLUTES), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Feet shoulder-width.", "Lower hips back and down.", "Keep chest up.", "Stand back up."),
            reps = "15-20"
        ),
        Exercise(
            id = "l2", name = "Lunges", description = "Unilateral leg strength.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.LEGS, MuscleGroup.GLUTES), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Step forward.", "Lower back knee to ground.", "Push back to start."),
            reps = "10 each leg"
        ),
        Exercise(
            id = "l3", name = "Glute Bridges", description = "Target glutes and hamstrings.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.GLUTES), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Lie on back, knees bent.", "Lift hips up.", "Squeeze glutes.", "Lower."),
            reps = "15-20"
        ),
        Exercise(
            id = "l4", name = "Wall Sit", description = "Isometric leg endurance.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.LEGS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Lean against wall.", "Slide down until knees at 90 degrees.", "Hold."),
            durationSeconds = 45
        ),

        // CHEST & ARMS
        Exercise(
            id = "u1", name = "Push-ups", description = "Classic upper body builder.",
            difficulty = Difficulty.INTERMEDIATE, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Hands shoulder-width.", "Lower chest to ground.", "Push up."),
            reps = "10-15"
        ),
        Exercise(
            id = "u2", name = "Tricep Dips", description = "Target back of arms.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE), // Can use chair
            muscleGroups = listOf(MuscleGroup.TRICEPS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Hands on chair/bench.", "Lower hips.", "Push back up."),
            reps = "10-12"
        ),
        Exercise(
            id = "u3", name = "Arm Circles", description = "Shoulder warmup and toning.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.SHOULDERS), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Arms out to sides.", "Make small circles.", "Reverse direction."),
            durationSeconds = 30
        ),
        Exercise(
            id = "u4", name = "Diamond Push-ups", description = "Tricep focused push-up.",
            difficulty = Difficulty.ADVANCED, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.TRICEPS, MuscleGroup.CHEST), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Hands close together forming diamond.", "Lower chest.", "Push up."),
            reps = "8-10"
        ),

        // BACK
        Exercise(
            id = "b1", name = "Superman", description = "Lower back strengthener.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.BACK), category = ExerciseCategory.STRENGTH,
            instructions = listOf("Lie on stomach.", "Lift arms and legs.", "Hold.", "Lower."),
            reps = "12-15"
        ),
        Exercise(
            id = "b2", name = "Bird Dog", description = "Core and back stability.",
            difficulty = Difficulty.BEGINNER, equipment = listOf(Equipment.NONE),
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.ABS), category = ExerciseCategory.BALANCE,
            instructions = listOf("On hands and knees.", "Extend opposite arm and leg.", "Hold.", "Switch."),
            reps = "10 each side"
        )
    )

    fun getExercisesByMuscle(group: MuscleGroup): List<Exercise> {
        return exercises.filter { it.muscleGroups.contains(group) }
    }

    fun getExercisesByDifficulty(level: Difficulty): List<Exercise> {
        return exercises.filter { it.difficulty == level }
    }
}
