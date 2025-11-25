# Workout Tracker App - Code Structure Guide
## For Non-Mobile Developers

This guide explains the structure and organization of your Android workout tracking application.

---

## 📱 What is This App?

A native Android fitness application that helps users:
- Track gym workouts with exercises, sets, reps, and weights
- View workout history and progress
- Calculate BMI (Body Mass Index)
- Manage exercise libraries
- Generate workout plans
- Log active workouts with rest timers

---

## 🏗️ Overall Project Structure

```
workout/
├── app/                          # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/workout/    # All Kotlin source code
│   │   │   ├── res/                          # Resources (UI layouts, images, strings)
│   │   │   └── AndroidManifest.xml          # App configuration
│   │   └── test/                             # Unit tests
│   └── build.gradle.kts                      # App-level build configuration
├── gradle/                                    # Gradle wrapper (build tool)
├── build.gradle.kts                          # Project-level build configuration
├── settings.gradle.kts                       # Project settings
└── README.md                                 # Project documentation
```

---

## 🎯 Android App Architecture Concepts

### **Think of it like a web application:**

| Android Concept | Web Equivalent | Purpose |
|----------------|----------------|---------|
| **Activity** | HTML Page | The main entry point/screen of the app |
| **Composable Functions** | React Components | Reusable UI building blocks |
| **ViewModel** | Redux Store/State Manager | Manages UI state and business logic |
| **Repository** | Service Layer | Handles data operations |
| **Database (Room)** | SQL Database | Persistent data storage |
| **DAO** | Database Query Interface | Database access layer |
| **Models/Entities** | Data Models/DTOs | Data structure definitions |

---

## 📂 Detailed Code Structure

### **1. Root Package: `com.example.workout`**

#### **📄 MainActivity.kt** (Entry Point)
- **What it is**: The main "page" that launches when you open the app
- **What it does**:
  - Shows a splash screen with animation
  - Sets up navigation between different screens
  - Manages which screen is currently visible
- **Key Functions**:
  - `onCreate()` - Initializes the app
  - `SplashScreen()` - Displays app logo with fade-in animation
  - `MainNavigation()` - Controls routing between screens

#### **📄 BMIScreen.kt**
- **What it is**: A screen for calculating Body Mass Index
- **What it does**:
  - Accepts user weight and height input
  - Calculates BMI score
  - Shows health category (Underweight/Normal/Overweight/Obese)
  - Supports both Metric (kg/cm) and Imperial (lbs/ft) units

---

### **2. Data Layer: `data/`** 
*Handles all data-related operations (like backend services)*

#### **📁 data/Models.kt**
- **Data structures** used throughout the app:
  - `Exercise` - Represents a gym exercise (name, muscle group, difficulty)
  - `WorkoutPlan` - A collection of exercises for a workout
  - `WorkoutLog` - Records of completed workouts
  - `SetLog` - Individual set data (reps, weight, rest time)

#### **📁 data/ExerciseRepository.kt**
- **Purpose**: Provides pre-defined exercise database
- **Contains**: 60+ exercises categorized by muscle groups:
  - Chest (bench press, push-ups, etc.)
  - Back (pull-ups, rows, etc.)
  - Legs (squats, lunges, etc.)
  - Shoulders, Arms, Core, Cardio
- **Functions**:
  - `getAllExercises()` - Get all exercises
  - `getExercisesByMuscleGroup()` - Filter by muscle group
  - `searchExercises()` - Search by name

#### **📁 data/WorkoutGenerator.kt**
- **Purpose**: Automatically creates workout plans
- **What it does**:
  - Generates workout routines based on goals
  - Creates 3-day, 4-day, or 5-day split programs
  - Examples: PPL (Push/Pull/Legs), Upper/Lower, Full Body
- **Functions**:
  - `generatePPL()` - Push, Pull, Legs routine
  - `generateUpperLower()` - Upper/Lower body split
  - `generateFullBody()` - Full body workouts

---

### **3. Database Layer: `data/database/`**
*Local storage - like a SQL database on the phone*

#### **📁 database/Entities.kt**
- **Database tables** defined as Kotlin classes:
  - `WorkoutLogEntity` - Stores completed workout records
  - Each class has `@Entity` annotation (marks it as a database table)
  - Fields have `@ColumnInfo` (defines column names)

#### **📁 database/WorkoutDao.kt**
- **DAO = Data Access Object** (like SQL query functions)
- **What it does**: Defines how to interact with the database
- **Key operations**:
  - `insertWorkoutLog()` - Save a workout
  - `getAllWorkoutLogs()` - Retrieve all workouts
  - `getWorkoutLogsByDateRange()` - Filter by date
  - `deleteWorkoutLog()` - Remove a workout
- **Special**: Uses `@Query` annotations (similar to SQL queries)

#### **📁 database/WorkoutDatabase.kt**
- **Purpose**: Database configuration and setup
- **What it is**: The actual database instance
- **Pattern**: Singleton (only one database instance exists)
- **Contains**: Reference to all DAOs and Entities

---

### **4. UI Layer: `ui/`**
*All user interface screens (like React components)*

#### **📁 ui/WorkoutPlanScreen.kt**
- **Purpose**: Select/Create workout plans
- **Features**:
  - Choose from pre-generated workout programs
  - View exercise lists for each day
  - Start a workout session

#### **📁 ui/WorkoutLoggingScreen.kt**
- **Purpose**: Active workout tracking
- **Features**:
  - Log sets, reps, and weights in real-time
  - Rest timer between sets
  - Progress tracking during workout
  - Save completed workout

#### **📁 ui/WorkoutInputScreen.kt**
- **Purpose**: Custom workout creation
- **Features**:
  - Add exercises to a workout
  - Set target sets and reps
  - Create personalized routines

#### **📁 ui/ExerciseDetailScreen.kt**
- **Purpose**: Detailed exercise information
- **Features**:
  - Exercise description
  - Target muscle groups
  - Difficulty level
  - Equipment needed

#### **📁 ui/HistoryScreen.kt**
- **Purpose**: View past workouts
- **Features**:
  - List of completed workouts by date
  - Total volume lifted
  - Workout duration
  - Detailed exercise breakdown

#### **📁 ui/SettingsScreen.kt**
- **Purpose**: App configuration
- **Features**:
  - User profile setup (age, gender)
  - Preferences
  - Health Connect integration

#### **📁 ui/WorkoutViewModel.kt**
- **Purpose**: Manages UI state
- **What it does**: Bridges UI and data layer
- **Pattern**: MVVM (Model-View-ViewModel)

#### **📁 ui/theme/** (Design System)
- **Color.kt** - App color palette
- **Theme.kt** - Light/Dark theme configuration
- **Type.kt** - Typography (font styles and sizes)

---

### **5. Health Integration: `health/`**

#### **📁 health/HealthConnectManager.kt**
- **Purpose**: Integration with Google Health Connect
- **What it does**:
  - Syncs workout data with Google Fit
  - Reads health metrics
  - Writes exercise sessions
- **Note**: Requires user permissions

---

### **6. Utilities: `utils/`**
- Helper functions and extensions
- Common utilities used across the app

---

## 🔄 How Data Flows (Request-Response Cycle)

### **Example: Logging a Workout**

```
1. USER ACTION
   └─> User fills workout form in WorkoutLoggingScreen.kt

2. UI LAYER (Composable)
   └─> Calls ViewModel function: saveWorkout()

3. VIEW MODEL
   └─> Validates data
   └─> Calls Repository

4. REPOSITORY
   └─> Transforms UI data to Database Entity
   └─> Calls DAO

5. DAO (Database Access)
   └─> Executes SQL INSERT query
   └─> Saves to Room Database

6. DATABASE
   └─> Stores data persistently on device

7. RESPONSE
   └─> Success/Failure flows back through layers
   └─> UI updates to show confirmation
```

---

## 🛠️ Key Technologies Used

### **1. Kotlin**
- Modern programming language for Android
- Similar to Java but more concise
- **Key features**: Null safety, coroutines, extension functions

### **2. Jetpack Compose**
- **Declarative UI framework** (like React)
- Build UIs with functions instead of XML
- **Example**:
  ```kotlin
  @Composable
  fun Greeting(name: String) {
      Text(text = "Hello, $name!")
  }
  ```

### **3. Room Database**
- **Local SQLite database wrapper**
- Type-safe database access
- Automatic query generation

### **4. Navigation Component**
- Handles screen transitions
- Manages back stack
- Deep linking support

### **5. Material Design 3**
- Google's design system
- Pre-built UI components
- Consistent styling

### **6. Kotlin Coroutines**
- Asynchronous programming
- Background tasks without blocking UI
- **Think of it as**: async/await in JavaScript

---

## 📱 Navigation Structure (User Flow)

```
Splash Screen (3 seconds)
    ↓
Main Navigation Host
    ├── Workout Plan (Home)
    ├── Exercise Library
    ├── Active Workout → Workout Logging
    ├── History
    ├── BMI Calculator
    └── Settings
```

**Routes** (like URL paths):
- `"workoutPlan"` - Main workout selection screen
- `"exerciseDetail/{exerciseId}"` - Exercise details (with ID parameter)
- `"workoutLogging/{planName}"` - Active workout tracking
- `"workoutInput"` - Custom workout creation
- `"history"` - Past workouts
- `"bmi"` - BMI calculator
- `"settings"` - App settings

---

## 🎨 UI Composition Pattern

**Jetpack Compose uses @Composable functions:**

```kotlin
@Composable
fun WorkoutCard(workout: WorkoutPlan) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = workout.name)
            Text(text = workout.description)
            Button(onClick = { startWorkout() }) {
                Text("Start")
            }
        }
    }
}
```

**This is similar to React components:**
```javascript
function WorkoutCard({ workout }) {
    return (
        <div className="card">
            <h2>{workout.name}</h2>
            <p>{workout.description}</p>
            <button onClick={startWorkout}>Start</button>
        </div>
    );
}
```

---

## 📦 Build System (Gradle)

### **build.gradle.kts** files define:
- Dependencies (libraries used)
- Build configurations
- Versioning

**Key dependencies in this app:**
- `androidx.compose.*` - Jetpack Compose UI
- `androidx.room.*` - Database
- `androidx.navigation.*` - Navigation
- `androidx.health.connect.*` - Health integration

---

## 🗃️ Resources (`res/` folder)

### **res/drawable/**
- Images, icons, and graphics
- App launcher icons

### **res/values/**
- `strings.xml` - All text displayed in the app (enables translations)
- `colors.xml` - Color definitions
- `themes.xml` - App themes

### **res/mipmap/**
- App icons for different screen densities

---

## 🔐 AndroidManifest.xml

**Purpose**: App configuration file (like package.json in Node.js)

**Defines**:
- App package name: `com.example.workout`
- Permissions needed (storage, health data)
- Main entry point (MainActivity)
- App icon and label

---

## 💡 Key Concepts for Non-Mobile Developers

### **1. Activity Lifecycle**
Activities (screens) have lifecycle events:
- `onCreate()` - Screen is created (like componentDidMount)
- `onStart()` - Screen becomes visible
- `onResume()` - User can interact
- `onPause()` - User leaves screen
- `onDestroy()` - Screen is destroyed

### **2. State Management**
- Use `remember` to store state within composables
- Use `ViewModel` for screen-level state
- Use `Repository` for app-level data

### **3. Reactive UI**
- UI automatically updates when state changes
- Similar to React's state and props

### **4. Kotlin Syntax Highlights**

**Data Classes** (like TypeScript interfaces with methods):
```kotlin
data class Exercise(
    val name: String,
    val muscleGroup: String,
    val difficulty: String
)
```

**Lambdas** (arrow functions):
```kotlin
val doubled = numbers.map { it * 2 }
```

**Null Safety**:
```kotlin
val length: Int? = text?.length  // Won't crash if text is null
```

---

## 🚀 How to Modify the App

### **To add a new screen:**
1. Create new Composable function in `ui/` folder
2. Add route in `MainNavigation()` in MainActivity.kt
3. Add navigation button to navigate to the screen

### **To add a new data model:**
1. Define data class in `Models.kt`
2. Create Entity in `database/Entities.kt`
3. Add DAO methods in `WorkoutDao.kt`
4. Update database version in `WorkoutDatabase.kt`

### **To modify UI:**
1. Locate the screen file in `ui/` folder
2. Edit the @Composable function
3. Compose automatically rebuilds UI

### **To add a new exercise:**
1. Open `ExerciseRepository.kt`
2. Add to appropriate muscle group list
3. That's it! It's automatically available

---

## 📊 App Data Storage

**Two types of data:**

1. **Persistent** (Room Database):
   - Workout logs
   - User progress
   - Saved routines

2. **In-Memory** (Variables):
   - Current workout state
   - UI selections
   - Temporary data

---

## 🧪 Testing

- **Unit tests**: Located in `app/src/test/`
- **Test files**: Match source file names with `Test` suffix
- Example: `ExerciseRepositoryTest.kt`

---

## 🎓 Learning Resources

If you want to dive deeper:

1. **Kotlin Basics**: https://kotlinlang.org/docs/basic-syntax.html
2. **Jetpack Compose**: https://developer.android.com/jetpack/compose
3. **Room Database**: https://developer.android.com/training/data-storage/room
4. **Android Architecture**: https://developer.android.com/topic/architecture

---

## 📝 Quick Reference

| File | Purpose |
|------|---------|
| MainActivity.kt | App entry point, navigation setup |
| Models.kt | Data structures |
| ExerciseRepository.kt | Exercise database |
| WorkoutDao.kt | Database queries |
| WorkoutLoggingScreen.kt | Active workout tracking |
| HistoryScreen.kt | Past workouts display |
| BMIScreen.kt | BMI calculator |

---

## 🎯 Summary

This Android app follows **MVVM architecture**:
- **Model**: Data classes, Repository, Database (data layer)
- **View**: Composable UI functions (ui layer)
- **ViewModel**: State management bridge (connects View and Model)

**Data flows**: View → ViewModel → Repository → DAO → Database

The app is **offline-first**, storing all data locally using Room Database, with optional Health Connect sync.

---

**Need help with a specific part? Ask about:**
- How a specific screen works
- How to add a feature
- Understanding a particular file
- Debugging an issue
