# Workout Tracker - Android App 🏋️

A comprehensive native Android fitness application for tracking gym workouts, managing exercise libraries, and monitoring your fitness progress.

---

## ✨ Features

### 🏋️ **Workout Management**
- **Pre-built Workout Plans**: PPL (Push/Pull/Legs), Upper/Lower, Full Body splits
- **Custom Workouts**: Create your own personalized routines
- **Exercise Library**: 60+ exercises across all muscle groups
- **Active Workout Tracking**: Log sets, reps, and weights in real-time
- **Rest Timer**: Countdown timer between sets

### 📊 **Progress Tracking**
- **Workout History**: View all past workouts with detailed breakdown
- **Progress Charts**: Track volume, strength, and consistency over time
- **Statistics**: Total workouts, volume lifted, and personal records

### 💪 **Health & Fitness**
- **BMI Calculator**: Calculate Body Mass Index with metric/imperial units
- **Health Connect Integration**: Sync with Google Fit and other health apps
- **Profile Management**: Age, gender, fitness goals

### 🎨 **User Experience**
- **Material Design 3**: Modern, beautiful interface
- **Dark Mode**: Easy on the eyes during late-night workouts
- **Offline First**: All data stored locally, no internet required
- **Smooth Animations**: Polished, premium feel

---

## 🏗️ Architecture

This app follows **MVVM (Model-View-ViewModel)** architecture with clean separation of concerns:

```
📱 UI Layer (Jetpack Compose)
    ↕️
🧠 ViewModel (State Management)
    ↕️
📦 Repository (Business Logic)
    ↕️
💾 Room Database (Local Storage)
```

### **Tech Stack:**
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Declarative UI)
- **Database**: Room (SQLite wrapper)
- **Navigation**: Navigation Component
- **Architecture**: MVVM + Repository Pattern
- **Design**: Material Design 3
- **Async**: Kotlin Coroutines + Flow

---

## 📂 Project Structure

```
app/src/main/java/com/example/workout/
├── MainActivity.kt              # App entry point & navigation
├── BMIScreen.kt                 # BMI calculator
│
├── data/
│   ├── Models.kt                # Data classes
│   ├── ExerciseRepository.kt    # Exercise database
│   ├── WorkoutGenerator.kt      # Auto-generate workouts
│   └── database/
│       ├── Entities.kt          # Database tables
│       ├── WorkoutDao.kt        # Database queries
│       └── WorkoutDatabase.kt   # Database config
│
├── ui/
│   ├── WorkoutPlanScreen.kt     # Select workout plans
│   ├── WorkoutLoggingScreen.kt  # Active workout tracking
│   ├── WorkoutInputScreen.kt    # Create custom workouts
│   ├── ExerciseDetailScreen.kt  # Exercise information
│   ├── HistoryScreen.kt         # Past workouts
│   ├── SettingsScreen.kt        # App settings
│   ├── WorkoutViewModel.kt      # State management
│   └── theme/                   # Design system
│
├── health/
│   └── HealthConnectManager.kt  # Google Health Connect
│
└── utils/
    └── Quotes.kt                # Motivational quotes
```

---

## 🚀 Getting Started

### **Prerequisites:**
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK 24 (min) to 34 (target)

### **How to Run:**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/satishpatibandla/workout_yeah_buddy.git
   cd workout_yeah_buddy
   ```

2. **Open in Android Studio:**
   - File → Open → Select project folder
   - Wait for Gradle sync to complete

3. **Run the app:**
   - Click the green "Run" button (▶️)
   - Select an emulator or connected device
   - App will install and launch automatically

---

## 📱 Screens Overview

| Screen | Purpose |
|--------|---------|
| **Splash Screen** | Animated app logo on startup |
| **Workout Plan** | Select from pre-built programs or create custom |
| **Exercise Library** | Browse 60+ exercises by muscle group |
| **Active Workout** | Log exercises with timer during workout |
| **Workout Input** | Create personalized workout routines |
| **History** | View past workouts and progress |
| **BMI Calculator** | Calculate Body Mass Index |
| **Settings** | User profile and app preferences |

---

## 💾 Database Schema

### **Tables:**

**workout_logs**
- `id` (Primary Key)
- `plan_name` (String)
- `date` (ISO String)
- `duration` (Int - minutes)
- `total_volume` (Int - kg)
- `exercises` (JSON String)

---

## 🎨 Design System

### **Colors:**
- Primary: Blue (#1976D2)
- Secondary: Green (#4CAF50)
- Background Light: (#F5F5F5)
- Background Dark: (#121212)

### **Typography:**
- Font Family: System Default (Roboto)
- Headings: Bold, 24sp-32sp
- Body: Regular, 14sp-16sp

---

## 🔄 Data Flow Example

**Logging a Workout:**
```
User Input → UI Screen → ViewModel → Repository → DAO → Room Database
```

**Viewing History:**
```
Database → DAO → Repository → ViewModel → UI (Auto-updates via Flow)
```

---

## 📚 Documentation

Detailed guides for understanding the codebase:

- **[CODE_STRUCTURE_GUIDE.md](./CODE_STRUCTURE_GUIDE.md)** - Comprehensive architecture guide
- **[QUICK_NAVIGATION_GUIDE.md](./QUICK_NAVIGATION_GUIDE.md)** - Quick reference for finding files

---

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

---

## 📦 Build

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

Output: `app/build/outputs/apk/`

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 🐛 Known Issues

- Health Connect requires manual permission setup
- Rest timer keeps running in background (by design)

---

## 🔮 Roadmap

- [ ] Export workout data to CSV
- [ ] Workout analytics dashboard
- [ ] Social features (share workouts)
- [ ] Custom exercise creation
- [ ] Video exercise demonstrations
- [ ] Workout templates from community

---

## 📄 License

This project is open source and available under the MIT License.

---

## 👨‍💻 Author

**Satish Patibandla**
- GitHub: [@satishpatibandla](https://github.com/satishpatibandla)

---

## 📞 Support

If you encounter any issues or have questions:
- Open an issue on GitHub
- Check the [documentation guides](./CODE_STRUCTURE_GUIDE.md)

---

## 🙏 Acknowledgments

- Exercise data curated from fitness research
- Icons from Material Design Icons
- Inspiration from fitness tracking apps

---

## 📸 Screenshots

*(Coming soon - add screenshots of your app in action)*

---

**Built with ❤️ using Kotlin & Jetpack Compose**

---

## 🔧 Configuration

### **Minimum Requirements:**
- Android 7.0 (API 24)
- 50 MB storage space

### **Recommended:**
- Android 10+ (API 29+)
- 100 MB storage space

### **Permissions:**
- HEALTH_CONNECT (optional - for Google Fit sync)
- INTERNET (optional - for future features)

---

## ⚡ Performance

- **App Size**: ~10 MB
- **Startup Time**: <1 second
- **Offline**: 100% functional without internet
- **Battery**: Minimal impact, optimized background tasks

---

**Happy Lifting! 💪🏋️**
