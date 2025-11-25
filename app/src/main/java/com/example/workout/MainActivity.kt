package com.example.workout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workout.ui.*
import com.example.workout.ui.theme.WorkoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkoutTheme {
                var showSplash by remember { mutableStateOf(true) }
                
                if (showSplash) {
                    SplashScreen(onAnimationEnd = { showSplash = false })
                } else {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 1500),
        label = "alpha"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        kotlinx.coroutines.delay(2500)
        onAnimationEnd()
    }

    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.splash_screen),
            contentDescription = "Yeah Buddy Splash",
            modifier = Modifier
                .fillMaxSize()
                .alpha(alphaAnim.value),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val workoutViewModel: WorkoutViewModel = viewModel()
    
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route?.startsWith("workout_logging") != true) {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            navController.navigate("bmi") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Home, "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            navController.navigate("history") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.History, "History") },
                        label = { Text("History") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                            navController.navigate("settings") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, "Settings") },
                        label = { Text("Settings") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "bmi",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("bmi") {
                selectedTab = 0
                BMIScreen(
                    onNavigateToWorkout = { bmi, age ->
                        navController.navigate("workout_input/$bmi/$age")
                    }
                )
            }

            composable(
                route = "workout_input/{bmi}/{age}",
                arguments = listOf(
                    navArgument("bmi") { type = NavType.FloatType },
                    navArgument("age") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val bmi = backStackEntry.arguments?.getFloat("bmi")?.toDouble() ?: 22.0
                val age = backStackEntry.arguments?.getInt("age") ?: 25
                WorkoutInputScreen(
                    bmi = bmi,
                    age = age,
                    onGenerateClick = { level, areas, time ->
                        workoutViewModel.generatePlan(bmi, age, level, areas, time)
                        navController.navigate("workout_plan")
                    }
                )
            }

            composable("workout_plan") {
                val plan = workoutViewModel.currentPlan
                if (plan != null) {
                    WorkoutPlanScreen(
                        plan = plan,
                        onExerciseClick = { exerciseId ->
                            navController.navigate("exercise_detail/$exerciseId")
                        },
                        onStartWorkout = {
                            navController.navigate("workout_logging")
                        }
                    )
                } else {
                    // Fallback
                    navController.popBackStack()
                }
            }
            
            composable("workout_logging") {
                val plan = workoutViewModel.currentPlan
                if (plan != null) {
                    WorkoutLoggingScreen(
                        plan = plan,
                        onComplete = {
                            navController.navigate("history") {
                                popUpTo("bmi")
                            }
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
            
            composable("history") {
                selectedTab = 1
                HistoryScreen()
            }
            
            composable("settings") {
                selectedTab = 2
                SettingsScreen()
            }

            composable(
                route = "exercise_detail/{exerciseId}",
                arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
                ExerciseDetailScreen(
                    exerciseId = exerciseId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}