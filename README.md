# JMC Event Manager - Android Tablet Application

## 📱 Application Overview

**JMC Event Manager** is a comprehensive Android tablet application developed for **Juba Management & Catering (JMC)** to streamline their event management process. The app provides a modern, intuitive interface for managing cultural, sports, educational, and community service events throughout the year.

---

## 🎯 Features

### Core Functionality
- **Event Dashboard** - Grid view of all events with real-time statistics
- **Event Creation & Management** - Comprehensive form for creating and managing events
- **Participant Registration** - User-friendly registration system with capacity tracking
- **Real-time Updates** - Live capacity monitoring and automatic status updates

### Event Categories
- 🎭 **Cultural** - Arts festivals, poetry nights, cultural events
- ⚽ **Sports** - Tournaments, sports competitions
- 📚 **Educational** - Workshops, seminars, training sessions
- 🤝 **Community Service** - Clean-up drives, volunteer activities

### Technical Features
- **Tablet-Optimized** - Responsive design specifically for tablet devices
- **Material Design 3** - Modern Android design principles
- **SQLite Database** - Robust local data persistence
- **Smooth Animations** - Frame and tween animations for enhanced UX
- **Transaction Management** - Safe database operations with rollback support

---

## 🏗️ Architecture

### Project Structure
```
app/
├── src/main/
│   ├── java/com/jmceventmanager/
│   │   ├── Activities/
│   │   │   ├── MainActivity.java
│   │   │   ├── EventManagementActivity.java
│   │   │   └── RegistrationActivity.java
│   │   ├── Adapters/
│   │   │   └── EventGridAdapter.java
│   │   ├── Database/
│   │   │   └── DatabaseHelper.java
│   │   ├── Models/
│   │   │   ├── ModelEvent.java
│   │   │   ├── Participant.java
│   │   │   └── Registration.java
│   │   └── Utilities/
│   ├── res/
│   │   ├── layout/
│   │   ├── values/
│   │   ├── anim/
│   │   └── drawable/
└── AndroidManifest.xml
```

### Database Schema
```sql
-- Events Table
Events (event_id, event_name, event_category, event_date_time, venue, 
        capacity, current_registrations, event_status, organizer_name)

-- Participants Table  
Participants (participant_id, participant_name, email, phone)

-- Registrations Table
Registrations (registration_id, event_id, participant_id, registration_timestamp)
```

---

## 🚀 Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 21+ (Android 5.0 Lollipop)
- Java 8 or later
- Android Tablet or Tablet Emulator

### Building the Project
1. **Clone or download the project**
2. **Open in Android Studio**
   ```bash
   File → Open → Select project folder
   ```
3. **Sync Gradle dependencies**
4. **Configure Virtual Device**
   - Go to: `Tools → AVD Manager`
   - Create new virtual device
   - Recommended: **Tablet (7-inch, 1920x1200)**
   - API Level: 21 or higher

5. **Build and Run**
   ```bash
   Run → Run 'app'
   ```

### Database Setup
The application automatically creates and initializes the SQLite database with sample data on first launch.

---

## 📖 Usage Guide

### 1. Event Dashboard (Main Activity)
- **View Events**: Browse all events in a responsive grid layout
- **Statistics**: Monitor active events, total events, and utilization rates
- **Event Details**: See capacity, status, and registration progress
- **Navigation**: Access event creation and registration features

### 2. Creating Events
- **Access**: Tap "Create Event" button from dashboard
- **Form Fields**:
  - Event Name, Category, Date & Time
  - Venue, Capacity, Organizer Name
  - Event Status (Active/Full/Cancelled/Completed)
- **Validation**: Real-time form validation with error messages
- **Date Picker**: User-friendly date and time selection

### 3. Participant Registration
- **Access**: Tap "Registrations" button from dashboard
- **Event Selection**: Choose from active events in dropdown
- **Participant Info**: Enter name, email, and phone number
- **Capacity Check**: Automatic validation against event capacity
- **Transaction Safety**: Database transactions prevent overbooking

---

## 🎨 UI/UX Features

### Design Principles
- **Material Design 3** - Google's latest design language
- **Tablet-First** - Optimized layouts for 7-inch+ screens
- **Accessibility** - Proper contrast ratios and touch targets
- **Consistency** - Uniform spacing and typography

### Visual Elements
- **Color-Coded Categories**:
  - Cultural: `#E91E63` (Pink)
  - Sports: `#4CAF50` (Green)
  - Educational: `#2196F3` (Blue) 
  - Community Service: `#FF9800` (Orange)

- **Status Indicators**:
  - Active: Green
  - Full: Orange
  - Cancelled: Red
  - Completed: Gray

### Animations
- **Grid Item Entrance** - Slide-up animations for event cards
- **Button Interactions** - Scale animations on press
- **Page Transitions** - Slide-in/slide-out between activities
- **Success Feedback** - Bounce animations for completed actions

---

## 🔧 Technical Specifications

### Minimum Requirements
- **API Level**: 21 (Android 5.0 Lollipop)
- **Screen Size**: 7-inch tablet or larger
- **Orientation**: Portrait and Landscape supported
- **Storage**: ~10MB free space

### Dependencies
```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
}
```

### Database Operations
- **CRUD Operations**: Full Create, Read, Update, Delete support
- **Transactions**: Atomic registration operations
- **Foreign Keys**: Referential integrity enforcement
- **Indexing**: Optimized query performance

---

## 📊 Sample Data

The application comes pre-loaded with sample events:

| Event Name | Category | Capacity | Status |
|------------|----------|----------|---------|
| Summer Arts Festival | Cultural | 60/60 | Active |
| Neighborhood Clean-up | Community Service | 67/80 | Active |
| Community Soccer Tournament | Sports | 32/32 | Full |
| Poetry Night | Cultural | 12/30 | Active |
| Digital Marketing Workshop | Educational | 18/25 | Active |
| Youth Leadership Summit | Educational | 0/50 | Cancelled |

---

## 🐛 Troubleshooting

### Common Issues

1. **App Crashes on Launch**
   - Check Android version compatibility (min API 21)
   - Verify all resource files are present
   - Clean and rebuild project

2. **Database Errors**
   - Clear app data: `Settings → Apps → JMC Event Manager → Storage → Clear Data`
   - Uninstall and reinstall application

3. **Layout Issues**
   - Ensure tablet emulator is properly configured
   - Check for missing drawable resources

4. **Registration Failures**
   - Verify event has available capacity
   - Check for duplicate participant registrations

### Debug Mode
Enable debug logging in `DatabaseHelper.java` for detailed database operations.

---

## 📝 Development Notes

### Code Standards
- **Package Naming**: `com.jmceventmanager.*`
- **Class Naming**: PascalCase (e.g., `EventManagementActivity`)
- **Method Naming**: camelCase (e.g., `setupEventListeners()`)
- **Resource Naming**: snake_case (e.g., `activity_main.xml`)

### Key Design Patterns
- **Model-View-Controller (MVC)** - Separation of concerns
- **Adapter Pattern** - For GridView data binding
- **Singleton Pattern** - Database helper instance management
- **Observer Pattern** - Event-driven user interactions

---

## 🔮 Future Enhancements

### Planned Features
- [ ] Cloud synchronization with web dashboard
- [ ] Push notifications for event updates
- [ ] QR code-based check-in system
- [ ] Advanced reporting and analytics
- [ ] Multi-language support
- [ ] Offline mode with sync capabilities

### Technical Improvements
- [ ] Migration to Room Database
- [ ] Jetpack Compose UI
- [ ] Kotlin coroutines for async operations
- [ ] Dependency injection with Hilt

---
## 📄 License

This project is developed for educational purposes as part of the HASD200-1 Android Systems Development module.

**Academic Year**: 2025 (July - December)  
**Course**: HPXS200-1 Praxis 2  
**Institution**: Your University Name  
**NQF Level**: 6, Credits: 20  

---

## 🎓 Learning Outcomes

This application demonstrates mastery of:
- ✅ MySQL/SQLite commands and transaction management
- ✅ Android tablet application development
- ✅ Modern UI/UX design principles
- ✅ Data persistence and management
- ✅ Professional code architecture
- ✅ Animation implementation
- ✅ Form validation and user input handling

---

**Built with ❤️ for Juba Management & Catering**
