package com.example.workout

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.pow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIScreen(
    modifier: Modifier = Modifier,
    onNavigateToWorkout: (Double, Int) -> Unit
) {
    var isMetric by remember { mutableStateOf(true) }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") } // cm for metric, ft for imperial
    var heightInches by remember { mutableStateOf("") } // Only for imperial

    var bmiResult by remember { mutableStateOf<Double?>(null) }
    var bmiCategory by remember { mutableStateOf("") }
    var bmiColor by remember { mutableStateOf(Color.Gray) }
    var healthMessage by remember { mutableStateOf("") }
    var idealWeightRange by remember { mutableStateOf("") }

    // Validation State
    var ageError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }
    var heightInchesError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // Shake Animations
    val ageShake = remember { Animatable(0f) }
    val weightShake = remember { Animatable(0f) }
    val heightShake = remember { Animatable(0f) }

    // Load saved unit preference
    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
        isMetric = sharedPref.getBoolean("IS_METRIC", true)
    }

    // Save unit preference
    fun saveUnitPreference(metric: Boolean) {
        val sharedPref = context.getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("IS_METRIC", metric)
            apply()
        }
    }

    fun shakeField(animatable: Animatable<Float, *>) {
        scope.launch {
            animatable.animateTo(10f, tween(50))
            animatable.animateTo(-10f, tween(50))
            animatable.animateTo(10f, tween(50))
            animatable.animateTo(0f, tween(50))
        }
    }

    fun validateAge(input: String): Boolean {
        if (input.isEmpty()) {
            ageError = "Required"
            return false
        }
        val ageVal = input.toIntOrNull()
        if (ageVal == null || ageVal < 2 || ageVal > 130) {
            ageError = "Age must be between 2 and 130"
            return false
        }
        ageError = null
        return true
    }

    fun validateWeight(input: String): Boolean {
        if (input.isEmpty()) {
            weightError = "Required"
            return false
        }
        val weightVal = input.toDoubleOrNull()
        if (weightVal == null) {
            weightError = "Invalid number"
            return false
        }
        if (isMetric) {
            if (weightVal < 10 || weightVal > 1000) {
                weightError = "10 - 1000 kg"
                return false
            }
        } else {
            if (weightVal < 22 || weightVal > 2205) {
                weightError = "22 - 2205 lbs"
                return false
            }
        }
        weightError = null
        return true
    }

    fun validateHeight(input: String, isFeet: Boolean = false): Boolean {
        if (input.isEmpty()) {
            if (isFeet) heightError = "Required" else if (isMetric) heightError = "Required" else heightInchesError = "Required"
            return false
        }
        val valNum = input.toDoubleOrNull()
        if (valNum == null) {
             if (isFeet) heightError = "Invalid" else if (isMetric) heightError = "Invalid" else heightInchesError = "Invalid"
            return false
        }

        if (isMetric) {
            if (valNum < 50 || valNum > 300) {
                heightError = "50 - 300 cm"
                return false
            }
            heightError = null
        } else {
            // Imperial validation
            if (isFeet) {
                if (valNum < 1 || valNum > 9) {
                    heightError = "1 - 9 ft"
                    return false
                }
                heightError = null
            } else {
                if (valNum < 0 || valNum >= 12) {
                    heightInchesError = "0 - 11 in"
                    return false
                }
                heightInchesError = null
            }
        }
        return true
    }

    val isFormValid = ageError == null && weightError == null && heightError == null && heightInchesError == null &&
            age.isNotEmpty() && weight.isNotEmpty() && height.isNotEmpty() && (isMetric || heightInches.isNotEmpty())

    fun calculateBMI() {
        // Final validation check before calc
        val vAge = validateAge(age)
        val vWeight = validateWeight(weight)
        val vHeight = validateHeight(height, !isMetric)
        val vHeightIn = if (!isMetric) validateHeight(heightInches, false) else true

        if (!vAge) shakeField(ageShake)
        if (!vWeight) shakeField(weightShake)
        if (!vHeight) shakeField(heightShake)
        // Note: heightInches shake separate if needed, or share heightShake

        if (!vAge || !vWeight || !vHeight || !vHeightIn) {
            Toast.makeText(context, "Please fix invalid fields", Toast.LENGTH_SHORT).show()
            return
        }

        val weightVal = weight.toDoubleOrNull()!!
        val heightVal = height.toDoubleOrNull()!!
        val heightInchesVal = heightInches.toDoubleOrNull() ?: 0.0

        val bmi: Double
        val heightM: Double

        if (isMetric) {
            heightM = heightVal / 100
            bmi = weightVal / heightM.pow(2)
        } else {
            val totalInches = heightVal * 12 + heightInchesVal
            // Imperial total height check
            if (totalInches < 20 || totalInches > 118) { // Approx 50cm - 300cm
                 Toast.makeText(context, "Total height out of range (1'8\" - 9'10\")", Toast.LENGTH_LONG).show()
                 return
            }
            bmi = 703 * weightVal / totalInches.pow(2)
            heightM = totalInches * 0.0254
        }

        bmiResult = bmi

        // Categorize
        when {
            bmi < 18.5 -> {
                bmiCategory = "Underweight"
                bmiColor = Color(0xFF2196F3) // Blue
                healthMessage = "You are underweight. Consider consulting a nutritionist."
            }
            bmi < 25 -> {
                bmiCategory = "Normal Weight"
                bmiColor = Color(0xFF4CAF50) // Green
                healthMessage = "Great job! You have a healthy body weight."
            }
            bmi < 30 -> {
                bmiCategory = "Overweight"
                bmiColor = Color(0xFFFF9800) // Orange
                healthMessage = "You are slightly overweight. Exercise and diet can help."
            }
            else -> {
                bmiCategory = "Obese"
                bmiColor = Color(0xFFF44336) // Red
                healthMessage = "You are in the obese range. Please consult a doctor."
            }
        }

        // Ideal Weight
        val minIdealWeightKg = 18.5 * heightM.pow(2)
        val maxIdealWeightKg = 24.9 * heightM.pow(2)

        if (isMetric) {
            idealWeightRange = String.format(Locale.US, "%.1f - %.1f kg", minIdealWeightKg, maxIdealWeightKg)
        } else {
            val minIdealWeightLbs = minIdealWeightKg * 2.20462
            val maxIdealWeightLbs = maxIdealWeightKg * 2.20462
            idealWeightRange = String.format(Locale.US, "%.1f - %.1f lbs", minIdealWeightLbs, maxIdealWeightLbs)
        }
    }

    fun reset() {
        age = ""
        weight = ""
        height = ""
        heightInches = ""
        bmiResult = null
        bmiCategory = ""
        healthMessage = ""
        idealWeightRange = ""
        ageError = null
        weightError = null
        heightError = null
        heightInchesError = null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Calculator",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Unit Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            UnitTab(
                text = "Metric (kg, cm)",
                selected = isMetric,
                onClick = {
                    isMetric = true
                    saveUnitPreference(true)
                    reset()
                }
            )
            UnitTab(
                text = "Imperial (lbs, ft/in)",
                selected = !isMetric,
                onClick = {
                    isMetric = false
                    saveUnitPreference(false)
                    reset()
                }
            )
        }

        // Inputs
        OutlinedTextField(
            value = age,
            onValueChange = { 
                if (it.length <= 3 && it.all { c -> c.isDigit() }) {
                    age = it
                    validateAge(it)
                }
            },
            label = { Text("Age") },
            isError = ageError != null,
            supportingText = { if (ageError != null) Text(ageError!!) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { translationX = ageShake.value },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { 
                weight = it
                validateWeight(it)
            },
            label = { Text(if (isMetric) "Weight (kg)" else "Weight (lbs)") },
            isError = weightError != null,
            supportingText = { if (weightError != null) Text(weightError!!) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { translationX = weightShake.value },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isMetric) {
            OutlinedTextField(
                value = height,
                onValueChange = { 
                    height = it
                    validateHeight(it)
                },
                label = { Text("Height (cm)") },
                isError = heightError != null,
                supportingText = { if (heightError != null) Text(heightError!!) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { translationX = heightShake.value },
                singleLine = true
            )
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = height,
                    onValueChange = { 
                        height = it
                        validateHeight(it, true)
                    },
                    label = { Text("Feet") },
                    isError = heightError != null,
                    supportingText = { if (heightError != null) Text(heightError!!) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer { translationX = heightShake.value },
                    singleLine = true
                )
                OutlinedTextField(
                    value = heightInches,
                    onValueChange = { 
                        heightInches = it
                        validateHeight(it, false)
                    },
                    label = { Text("Inches") },
                    isError = heightInchesError != null,
                    supportingText = { if (heightInchesError != null) Text(heightInchesError!!) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { calculateBMI() },
                enabled = isFormValid,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Calculate", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            }

            OutlinedButton(
                onClick = { reset() },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Results
        AnimatedVisibility(
            visible = bmiResult != null,
            enter = slideInVertically(initialOffsetY = { 40 }) + fadeIn(animationSpec = tween(500))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your BMI",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = String.format(Locale.US, "%.1f", bmiResult ?: 0.0),
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = bmiColor
                    )
                    Text(
                        text = bmiCategory,
                        style = MaterialTheme.typography.headlineSmall,
                        color = bmiColor,
                        fontWeight = FontWeight.Medium
                    )
                    
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                    
                    Text(
                        text = healthMessage,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = "Ideal Weight: $idealWeightRange",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { 
                    val ageVal = age.toIntOrNull() ?: 25 // Default if empty, though validation prevents this
                    bmiResult?.let { onNavigateToWorkout(it, ageVal) } 
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Get Personalized Workout Plan")
            }
        }
    }
}

@Composable
fun UnitTab(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(
                if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
