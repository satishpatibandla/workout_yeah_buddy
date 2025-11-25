package com.example.workout.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.workout.data.Difficulty
import com.example.workout.data.Equipment
import com.example.workout.data.MuscleGroup

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutInputScreen(
    bmi: Double,
    age: Int,
    onGenerateClick: (Difficulty, List<MuscleGroup>, Int) -> Unit
) {
    var bodyFat by remember { mutableStateOf(20f) }
    var selectedFitnessLevel by remember { mutableStateOf(Difficulty.BEGINNER) }
    var selectedTime by remember { mutableStateOf(30) }
    val selectedProblemAreas = remember { mutableStateListOf<MuscleGroup>() }
    
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Personalize Your Plan",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Body Fat
        Text("Body Fat Percentage: ${bodyFat.toInt()}%", style = MaterialTheme.typography.titleMedium)
        Slider(
            value = bodyFat,
            onValueChange = { bodyFat = it },
            valueRange = 5f..50f,
            steps = 45
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Fitness Level
        Text("Fitness Level", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Difficulty.values().forEach { level ->
                FilterChip(
                    selected = selectedFitnessLevel == level,
                    onClick = { selectedFitnessLevel = level },
                    label = { Text(level.name.lowercase().capitalize()) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Time
        Text("Time Available (min)", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(15, 30, 45, 60).forEach { time ->
                FilterChip(
                    selected = selectedTime == time,
                    onClick = { selectedTime = time },
                    label = { Text("$time min") }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Problem Areas
        Text("Target Areas (Select at least one)", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val areas: List<Pair<MuscleGroup, String>> = listOf(
                MuscleGroup.ABS to "Belly",
                MuscleGroup.LEGS to "Thighs",
                MuscleGroup.GLUTES to "Hips",
                MuscleGroup.BICEPS to "Arms",
                MuscleGroup.BACK to "Back",
                MuscleGroup.CHEST to "Chest"
            )
            
            areas.forEach { (group, label) ->
                FilterChip(
                    selected = selectedProblemAreas.contains(group),
                    onClick = {
                        if (selectedProblemAreas.contains(group)) {
                            selectedProblemAreas.remove(group)
                        } else {
                            selectedProblemAreas.add(group)
                        }
                    },
                    label = { Text(label) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (selectedProblemAreas.isEmpty()) {
                    // Show error (handled by caller or toast here? Caller is cleaner but for now just simple)
                    // For simplicity, default to Full Body if empty
                    selectedProblemAreas.add(MuscleGroup.FULL_BODY)
                }
                onGenerateClick(selectedFitnessLevel, selectedProblemAreas, selectedTime)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Workout")
        }
    }
}

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
