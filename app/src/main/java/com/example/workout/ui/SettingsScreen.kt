package com.example.workout.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
    var isMetric by remember { mutableStateOf(sharedPref.getBoolean("IS_METRIC", true)) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Use Metric Units")
                    Switch(
                        checked = isMetric,
                        onCheckedChange = {
                            isMetric = it
                            with(sharedPref.edit()) {
                                putBoolean("IS_METRIC", it)
                                apply()
                            }
                        }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isMetric) "kg, cm" else "lbs, ft/in",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("About", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Yeah Buddy - Workout & BMI Tracker")
                Text("Version 1.0.0", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text("\"Yeah Buddy! Light Weight!\"", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Text("- Ronnie Coleman", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
