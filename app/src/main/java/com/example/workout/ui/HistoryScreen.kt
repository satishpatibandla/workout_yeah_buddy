package com.example.workout.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workout.data.database.WorkoutDatabase
import com.example.workout.data.database.WorkoutLogEntity
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val database = remember { WorkoutDatabase.getDatabase(context) }
    val workouts = remember { mutableStateListOf<WorkoutLogEntity>() }
    val stats = remember { mutableStateOf(Pair(0, 0)) } // (total workouts, total minutes)
    
    LaunchedEffect(Unit) {
        database.workoutDao().getAllWorkouts().collectLatest { workoutList ->
            workouts.clear()
            workouts.addAll(workoutList)
        }
    }
    
    LaunchedEffect(Unit) {
        val totalWorkouts = database.workoutDao().getTotalCompletedWorkouts()
        val totalMinutes = database.workoutDao().getTotalMinutesExercised()
        stats.value = Pair(totalWorkouts, totalMinutes)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Workout History",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Statistics Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Your Stats", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Total Workouts", style = MaterialTheme.typography.bodyMedium)
                        Text("${stats.value.first}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("Total Minutes", style = MaterialTheme.typography.bodyMedium)
                        Text("${stats.value.second}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        if (workouts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No workouts logged yet!")
                    Text("Complete your first workout to see it here.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(workouts) { workout ->
                    WorkoutHistoryCard(workout)
                }
            }
        }
    }
}

@Composable
fun WorkoutHistoryCard(workout: WorkoutLogEntity) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
    
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = workout.workoutName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dateFormat.format(Date(workout.date)),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Duration: ${workout.durationMinutes} min", style = MaterialTheme.typography.bodyMedium)
                if (workout.completed) {
                    Text("✓ Completed", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
