package com.example.workout.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workout.data.WorkoutPlan
import com.example.workout.data.WorkoutExercise
import com.example.workout.data.database.WorkoutDatabase
import com.example.workout.data.database.WorkoutLogEntity
import com.example.workout.data.database.ExerciseLogEntity
import com.example.workout.utils.MotivationalQuotes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutLoggingScreen(
    plan: WorkoutPlan,
    onComplete: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { WorkoutDatabase.getDatabase(context) }
    
    var currentExerciseIndex by remember { mutableStateOf(0) }
    var completedSets by remember { mutableStateOf(List(plan.exercises.size) { 0 }) }
    var startTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val currentExercise = plan.exercises.getOrNull(currentExerciseIndex)
    
    val progress = (currentExerciseIndex + 1).toFloat() / plan.exercises.size
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Active Workout") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.Check, "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (currentExercise == null) {
            // Workout Complete
            WorkoutCompleteView(
                plan = plan,
                startTime = startTime,
                onFinish = {
                    scope.launch {
                        val workoutId = database.workoutDao().insertWorkoutLog(
                            WorkoutLogEntity(
                                workoutPlanId = plan.id,
                                workoutName = plan.name,
                                date = System.currentTimeMillis(),
                                startTime = startTime,
                                endTime = System.currentTimeMillis(),
                                durationMinutes = ((System.currentTimeMillis() - startTime) / 60000).toInt(),
                                completed = true,
                                notes = null
                            )
                        )
                        onComplete()
                    }
                },
                modifier = Modifier.padding(padding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Exercise ${currentExerciseIndex + 1} of ${plan.exercises.size}",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentExercise.exercise.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        if (currentExercise.durationSeconds != null) {
                            Text("Duration: ${currentExercise.durationSeconds} seconds")
                        } else {
                            Text("${currentExercise.sets} sets × ${currentExercise.reps} reps")
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Rest: ${currentExercise.restSeconds}s between sets")
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text("Instructions:", fontWeight = FontWeight.Bold)
                        currentExercise.exercise.instructions.forEach { instruction ->
                            Text("• $instruction", style = MaterialTheme.typography.bodyMedium)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text("Completed Sets: ${completedSets[currentExerciseIndex]}/${currentExercise.sets}")
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (currentExerciseIndex > 0) {
                        OutlinedButton(
                            onClick = { currentExerciseIndex-- },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Previous")
                        }
                    }
                    
                    Button(
                        onClick = { currentExerciseIndex++ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (currentExerciseIndex == plan.exercises.size - 1) "Finish" else "Next Exercise")
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutCompleteView(
    plan: WorkoutPlan,
    startTime: Long,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val duration = ((System.currentTimeMillis() - startTime) / 60000).toInt()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = MotivationalQuotes.getRandomQuote(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Workout Complete!", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Duration: $duration minutes")
        Text("Exercises: ${plan.exercises.size}")
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) {
            Text("Finish")
        }
    }
}
