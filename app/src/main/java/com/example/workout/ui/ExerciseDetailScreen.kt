package com.example.workout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workout.data.Exercise
import com.example.workout.data.ExerciseRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    onBackClick: () -> Unit
) {
    val exercise = remember(exerciseId) { ExerciseRepository.exercises.find { it.id == exerciseId } }

    if (exercise == null) {
        Text("Exercise not found")
        return
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Instructions", "Muscles", "Tips")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exercise.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Toggle Favorite */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                    }
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Hero Animation Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Animation Placeholder", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                // In real app: Coil Image or LottieAnimation here
            }

            // Quick Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                QuickStat("Difficulty", exercise.difficulty.name)
                QuickStat("Type", exercise.category.name)
                QuickStat("Target", exercise.muscleGroups.firstOrNull()?.name ?: "Full Body")
            }

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    0 -> InstructionsTab(exercise)
                    1 -> MusclesTab(exercise)
                    2 -> TipsTab(exercise)
                }
            }
        }
    }
}

@Composable
fun QuickStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun InstructionsTab(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Step-by-Step", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        exercise.instructions.forEachIndexed { index, step ->
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text((index + 1).toString(), color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(step, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun MusclesTab(exercise: Exercise) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Primary Muscles", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        FlowRow(modifier = Modifier.padding(top = 8.dp)) {
            exercise.muscleGroups.forEach { muscle ->
                SuggestionChip(
                    onClick = {},
                    label = { Text(muscle.name) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder for body map
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("Interactive Body Map Placeholder")
        }
    }
}

@Composable
fun TipsTab(exercise: Exercise) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (exercise.tips.isNotEmpty()) {
            exercise.tips.forEach { tip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = tip,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            Text("No specific tips for this exercise yet.")
        }
    }
}
