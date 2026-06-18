# Android Background Work Cookbook

An educational and practical guide demonstrating modern Android background processing techniques. This project explores how to execute tasks efficiently without compromising user experience, battery life, or system resources, following Modern Android Development (MAD) practices.

This repository is part of the [Android Modern Cookbook](https://github.com/dhruva-b-dev/android-modern-cookbook).

---

## 💳 Credits & Attribution

> ⚠️ **Important Note:** The core code implementations, logic, and architectural patterns used in this project are adapted from the [BackgroundWork](https://github.com/thearchetypee/BackgroundWork) repository developed by [@thearchetypee](https://github.com/thearchetypee).

---

## 🚀 Features & Components

This project demonstrates several ways to handle operations outside the main UI thread depending on constraints like persistence, execution timing, and system restrictions:

1. **WorkManager (Recommended for Persistent Work)**
    * **One-Time & Periodic Work Requests:** Scheduled tasks that survive process death and device reboots.
    * **Constraints:** Network state checking, battery level gating, and charging requirements.
    * **Expedited Work:** Instant execution for high-priority background tasks.

2. **Foreground Services & Notifications**
    * Long-running tasks noticeable to the user (e.g., file downloads, media playback, active tracking).
    * Safe initialization and runtime permission handling for Android 13+ (`POST_NOTIFICATIONS`).

3. **Coroutines & Asynchronous Flow**
    * Offloading non-blocking asynchronous calls to specific background dispatchers (`Dispatchers.IO`, `Dispatchers.Default`).

---

## 🛠 Tech Stack & Tools

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Modern, declarative UI layer)
* **Asynchronous Programming:** Kotlin Coroutines & Flow
* **Jetpack Architecture Components:** ViewModel, WorkManager

---

## 📦 Getting Started

### Prerequisites
* Android Studio (Ladybug or newer recommended)
* Android SDK 34+
* Gradle JDK 17+

### Installation & Setup

1. **Clone the repository:**
```bash
   git clone [https://github.com/dhruva-b-dev/android-modern-cookbook.git](https://github.com/dhruva-b-dev/android-modern-cookbook.git)
   cd android-modern-cookbook/AndroidBackgroundWork