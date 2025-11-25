# Quick Navigation Guide - Workout App

## 🗂️ File Structure Overview

```
workout/
│
├── 📱 app/
│   └── src/
│       └── main/
│           ├── 📝 java/com/example/workout/
│           │   │
│           │   ├── 🎯 MainActivity.kt                    ← APP ENTRY POINT
│           │   ├── 📊 BMIScreen.kt                       ← BMI Calculator Screen
│           │   │
│           │   ├── 📦 data/                              ← DATA LAYER
│           │   │   ├── Models.kt                         ← Data structures (Exercise, WorkoutPlan, etc.)
│           │   │   ├── ExerciseRepository.kt             ← 60+ pre-defined exercises
│           │   │   ├── WorkoutGenerator.kt               ← Auto-generate workout plans
│           │   │   │
│           │   │   └── 💾 database/                      ← LOCAL DATABASE
│           │   │       ├── Entities.kt                   ← Database tables
│           │   │       ├── WorkoutDao.kt                 ← Database queries (CRUD operations)
│           │   │       └── WorkoutDatabase.kt            ← Database configuration
│           │   │
│           │   ├── 🎨 ui/                                ← UI SCREENS
│           │   │   ├── WorkoutPlanScreen.kt              ← Select/view workout plans
│           │   │   ├── WorkoutLoggingScreen.kt           ← Active workout tracking
│           │   │   ├── WorkoutInputScreen.kt             ← Create custom workouts
│           │   │   ├── ExerciseDetailScreen.kt           ← Exercise information
│           │   │   ├── HistoryScreen.kt                  ← Past workouts
│           │   │   ├── SettingsScreen.kt                 ← App settings
│           │   │   ├── WorkoutViewModel.kt               ← State management
│           │   │   │
│           │   │   └── 🎨 theme/                         ← DESIGN SYSTEM
│           │   │       ├── Color.kt                      ← Color palette
│           │   │       ├── Theme.kt                      ← Light/Dark themes
│           │   │       └── Type.kt                       ← Typography
│           │   │
│           │   ├── 💚 health/                            ← HEALTH INTEGRATION
│           │   │   └── HealthConnectManager.kt           ← Google Health Connect sync
│           │   │
│           │   └── 🔧 utils/                             ← UTILITIES
│           │       └── Quotes.kt                         ← Motivational quotes
│           │
│           ├── 🎨 res/                                   ← RESOURCES
│           │   ├── drawable/                             ← Images & icons
│           │   ├── mipmap/                               ← App launcher icons
│           │   └── values/                               ← Strings, colors, themes
│           │       ├── strings.xml
│           │       ├── colors.xml
│           │       └── themes.xml
│           │
│           └── 📄 AndroidManifest.xml                    ← App configuration
│
├── 🔨 build.gradle.kts                                   ← Project build config
├── ⚙️ settings.gradle.kts                                ← Project settings
├── 📖 README.md                                          ← Basic project info
└── 📚 CODE_STRUCTURE_GUIDE.md                            ← Detailed guide (this file)
```

---

## 🎯 Quick File Finder

### **"I want to modify..."**

| What you want to do | Edit this file |
|---------------------|----------------|
| App startup/splash screen | `MainActivity.kt` |
| Navigation between screens | `MainActivity.kt` → `MainNavigation()` |
| BMI calculator | `BMIScreen.kt` |
| Workout plan selection | `ui/WorkoutPlanScreen.kt` |
| Active workout tracking | `ui/WorkoutLoggingScreen.kt` |
| Create custom workout | `ui/WorkoutInputScreen.kt` |
| View workout history | `ui/HistoryScreen.kt` |
| App settings | `ui/SettingsScreen.kt` |
| Exercise details | `ui/ExerciseDetailScreen.kt` |
| Add new exercises | `data/ExerciseRepository.kt` |
| Generate workout plans | `data/WorkoutGenerator.kt` |
| Database queries | `data/database/WorkoutDao.kt` |
| Data models | `data/Models.kt` |
| Database tables | `data/database/Entities.kt` |
| App colors | `ui/theme/Color.kt` |
| Dark/Light theme | `ui/theme/Theme.kt` |
| Font styles | `ui/theme/Type.kt` |
| Text strings | `res/values/strings.xml` |
| App permissions | `AndroidManifest.xml` |
| App dependencies | `app/build.gradle.kts` |

---

## 🔄 Data Flow Examples

### **Example 1: User Logs a Workout**

```
1. User → Opens app
   └─ MainActivity.kt (onCreate)

2. User → Navigates to "Start Workout"
   └─ MainActivity.kt (MainNavigation → "workoutLogging")

3. App → Shows WorkoutLoggingScreen
   └─ ui/WorkoutLoggingScreen.kt

4. User → Enters sets, reps, weight
   └─ UI state updates in real-time

5. User → Clicks "Save Workout"
   └─ WorkoutLoggingScreen calls ViewModel

6. ViewModel → Validates data
   └─ Calls WorkoutDao.insertWorkoutLog()

7. Database → Saves workout
   └─ data/database/WorkoutDao.kt → Room Database

8. App → Shows success message
   └─ Navigates to History Screen
```

---

### **Example 2: User Views Exercise Library**

```
1. User → Clicks "Exercise Library"
   └─ MainNavigation routes to ExerciseDetailScreen

2. App → Loads exercises from repository
   └─ data/ExerciseRepository.kt → getAllExercises()

3. App → Displays list grouped by muscle group
   └─ ui/ExerciseDetailScreen.kt renders UI

4. User → Searches for "bench press"
   └─ ExerciseRepository.searchExercises("bench press")

5. App → Shows filtered results
   └─ UI updates with matching exercises
```

---

### **Example 3: App Generates Workout Plan**

```
1. User → Selects "PPL (Push/Pull/Legs)"
   └─ ui/WorkoutPlanScreen.kt

2. App → Calls workout generator
   └─ data/WorkoutGenerator.kt → generatePPL()

3. Generator → Selects exercises from repository
   └─ data/ExerciseRepository.kt → getExercisesByMuscleGroup()

4. Generator → Creates 3-day workout plan
   └─ Returns WorkoutPlan objects

5. App → Displays plan to user
   └─ ui/WorkoutPlanScreen.kt renders workout cards
```

---

## 🧩 Key Components Explained

### **1. MainActivity.kt** - The Heart of the App
- **Line 1-48**: Imports all necessary libraries
- **Line 49-65**: `MainActivity` class - App entry point
- **Line 67-97**: `SplashScreen()` - Animated intro
- **Line 99-242**: `MainNavigation()` - All app screens and routing

**Navigation Routes:**
```kotlin
"workoutPlan"           → Home screen
"exerciseDetail/{id}"   → Exercise details (with parameter)
"workoutLogging/{plan}" → Active workout (with parameter)
"workoutInput"          → Create workout
"history"               → Past workouts
"bmi"                   → BMI calculator
"settings"              → Settings
```

---

### **2. Data Layer Files**

#### **Models.kt** - Data Structures
```kotlin
data class Exercise(
    val name: String,           // "Bench Press"
    val muscleGroup: String,    // "Chest"
    val difficulty: String      // "Intermediate"
)

data class WorkoutPlan(
    val name: String,           // "PPL - Push Day"
    val description: String,    // Plan details
    val exercises: List<Exercise>
)

data class WorkoutLog(
    val planName: String,
    val date: String,
    val duration: Int,          // minutes
    val exercises: List<SetLog>
)

data class SetLog(
    val exercise: String,
    val sets: Int,
    val reps: Int,
    val weight: Double
)
```

#### **ExerciseRepository.kt** - Exercise Database
- Contains 60+ exercises organized by muscle group
- Functions to retrieve, filter, and search exercises
- Pre-populated data (no API calls needed)

---

### **3. Database Layer**

#### **Entities.kt** - Database Tables
```kotlin
@Entity(tableName = "workout_logs")
data class WorkoutLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "plan_name") val planName: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "exercises") val exercises: String
)
```

#### **WorkoutDao.kt** - Database Operations
```kotlin
@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkoutLog(log: WorkoutLogEntity): Long
    
    @Query("SELECT * FROM workout_logs ORDER BY date DESC")
    fun getAllWorkoutLogs(): Flow<List<WorkoutLogEntity>>
    
    @Delete
    suspend fun deleteWorkoutLog(log: WorkoutLogEntity)
}
```

**Key Points:**
- `@Insert`, `@Query`, `@Delete` = SQL operations
- `suspend` = runs in background (async)
- `Flow` = live data stream (updates UI automatically)

---

### **4. UI Screens**

All screens follow this pattern:
```kotlin
@Composable
fun ScreenName(
    navController: NavController,  // For navigation
    viewModel: ViewModel           // For data
) {
    // 1. State variables
    var userInput by remember { mutableStateOf("") }
    
    // 2. UI Layout
    Column {
        Text("Title")
        TextField(value = userInput, onValueChange = { userInput = it })
        Button(onClick = { /* action */ }) {
            Text("Save")
        }
    }
}
```

---

## 📱 Screen Components Breakdown

### **WorkoutLoggingScreen.kt** (Most Complex Screen)

**Features:**
- ✅ Exercise list with add/remove
- ✅ Set tracking (reps, weight)
- ✅ Rest timer between sets
- ✅ Real-time workout duration
- ✅ Save to database

**Key sections:**
1. **State Management** - Tracks current workout
2. **Exercise Cards** - Displays each exercise
3. **Set Inputs** - Weight/reps input fields
4. **Timer** - Countdown between sets
5. **Save Function** - Writes to database

---

## 🎨 Theme System

### **Color.kt** - Color Palette
```kotlin
val PrimaryBlue = Color(0xFF1976D2)
val SecondaryGreen = Color(0xFF4CAF50)
val BackgroundLight = Color(0xFFF5F5F5)
val BackgroundDark = Color(0xFF121212)
```

### **Theme.kt** - Applies Colors
```kotlin
@Composable
fun WorkoutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(colorScheme = colors, content = content)
}
```

---

## 🔍 Debugging Tips

### **Common Files to Check:**

1. **App crashes on startup?**
   → Check `MainActivity.kt` → `onCreate()`

2. **Navigation not working?**
   → Check `MainActivity.kt` → `MainNavigation()`

3. **Data not saving?**
   → Check `WorkoutDao.kt` → Database functions

4. **UI not updating?**
   → Check state variables in screen files

5. **Exercise not showing?**
   → Check `ExerciseRepository.kt`

---

## 🚀 Common Modifications

### **Adding a New Screen:**

1. Create new file in `ui/` folder:
```kotlin
// ui/NewScreen.kt
@Composable
fun NewScreen(navController: NavController) {
    Column {
        Text("New Screen")
    }
}
```

2. Add route in `MainActivity.kt`:
```kotlin
composable("newScreen") {
    NewScreen(navController)
}
```

3. Navigate to it:
```kotlin
navController.navigate("newScreen")
```

---

### **Adding a New Exercise:**

Open `ExerciseRepository.kt`, find appropriate muscle group:
```kotlin
private val chestExercises = listOf(
    Exercise("Bench Press", "Chest", "Intermediate"),
    Exercise("Your New Exercise", "Chest", "Beginner")  // Add here
)
```

---

### **Changing App Colors:**

1. Open `ui/theme/Color.kt`
2. Modify color values:
```kotlin
val PrimaryBlue = Color(0xFF2196F3)  // Change hex code
```

---

## 📊 Database Schema

```
workout_logs (table)
├── id (Primary Key, Auto-increment)
├── plan_name (Text)
├── date (Text, ISO format)
├── duration (Integer, minutes)
├── total_volume (Integer, kg)
└── exercises (JSON Text)
```

---

## 🎓 Code Patterns Used

### **1. Singleton Pattern** (Database)
```kotlin
companion object {
    @Volatile
    private var INSTANCE: WorkoutDatabase? = null
}
```
Ensures only one database instance exists.

---

### **2. Repository Pattern** (Data Access)
```kotlin
class ExerciseRepository {
    fun getAllExercises() = allExercises
}
```
Centralizes data access logic.

---

### **3. Observer Pattern** (LiveData/Flow)
```kotlin
fun getAllWorkoutLogs(): Flow<List<WorkoutLogEntity>>
```
UI automatically updates when data changes.

---

### **4. Composable Pattern** (UI)
```kotlin
@Composable
fun ReusableComponent() { }
```
Build UI with reusable functions.

---

## 📞 Quick Help

**Question: How do I...?**

| Task | File(s) to Edit |
|------|-----------------|
| Change app name | `res/values/strings.xml` |
| Change app icon | `res/mipmap/` folders |
| Add database field | `Entities.kt`, `WorkoutDao.kt` |
| Modify workout generator | `WorkoutGenerator.kt` |
| Change rest timer duration | `WorkoutLoggingScreen.kt` |
| Add new muscle group | `ExerciseRepository.kt`, `Models.kt` |
| Change theme colors | `ui/theme/Color.kt` |
| Add new permission | `AndroidManifest.xml` |

---

## 🎯 Summary

**This app is organized into 3 main layers:**

1. **UI Layer** (`ui/` folder)
   - User interface screens
   - Composable functions
   - User interactions

2. **Business Logic** (`data/` folder)
   - ExerciseRepository
   - WorkoutGenerator
   - Data models

3. **Data Layer** (`data/database/` folder)
   - Room Database
   - DAO (queries)
   - Entities (tables)

**Data flows:** UI → ViewModel → Repository → DAO → Database

**Everything is connected through:**
- Navigation (MainActivity)
- ViewModels (state management)
- Repository (data access)

---

**Still confused? Open any file and look for:**
- `@Composable` = UI function
- `@Entity` = Database table
- `@Dao` = Database queries
- `data class` = Data structure
- `fun` = Function/method

You're now ready to explore and modify the codebase! 🚀
