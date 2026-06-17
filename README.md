# 🍳 Android Modern Cookbook

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-1.7.0-green.svg?style=flat&logo=android)](https://developer.android.com/jetpack/compose)
[![Platform](https://img.shields.io/badge/Platform-Android-lightgrey.svg?style=flat&logo=android)](https://developer.android.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Welcome to the **Android Modern Cookbook** repository! This project serves as a comprehensive, hands-on reference guide for modern Android Development (MAD). It features a curated collection of standalone "recipes," best practices, and code snippets demonstrating how to build robust, scalable, and high-performance applications using the latest Jetpack libraries.

---

## 🚀 Key Features & Recipes

This cookbook is structured into independent recipes or modules focusing on specific core aspects of modern Android engineering:

- **Jetpack Compose UI & Material 3:** Declarative UI layouts, dynamic theming (Dark/Light mode), complex animations, and custom modifiers.
- **Modern Architecture (MVVM/MVI):** Clean Architecture principles leveraging Unidirectional Data Flow (UDF) with `StateFlow` and `SharedFlow`.
- **Asynchronous & Reactive Programming:** Robust background processing utilizing Kotlin Coroutines and asynchronous Data Streaming with Flows.
- **Local Data Persistence:** Modern local storage implementation using **Room Database** (with relational mapping) and **Jetpack DataStore** (Preferences/Proto) for key-value pairings.
- **Network & API Integration:** Type-safe HTTP networking via **Retrofit** paired with OkHttp, interceptors, and serialization tools.
- **Dependency Injection:** Modular, clean dependency management using **Hilt** (Dagger) or **Koin**.
- **Background Work:** Scheduling deferrable, guaranteed background operations via **WorkManager**.

---

## 🛠 Tech Stack & Libraries

- **Language:** [Kotlin](https://kotlinlang.org/) (Coroutines, Flow)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material Design 3](https://m3.material.io/)
- **Architecture:** MVVM / MVI pattern with Clean Architecture separation
- **Dependency Injection:** [Hilt / Dagger](https://developer.android.com/training/dependency-injection/hilt-android)
- **Local Storage:** [Room](https://developer.android.com/training/data-storage/room), [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **Networking:** [Retrofit](https://square.github.io/retrofit/) & OkHttp3
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/)
- **Jetpack Libraries:** Lifecycle, ViewModel, Navigation-Compose, WorkManager

---

## 📂 Project Structure

The project follows a feature-focused modular structure or clear package segregation inside the main app module:

```text
app/
└── src/
    └── main/
        └── java/com/example/cookbook/
            ├── data/         # Repositories, Local DB, Remote API sources
            ├── domain/       # Use Cases, Core Domain Models, Business Rules
            ├── ui/           # Compose Screens, Navigation Component, ViewModels
            │   ├── common/   # Reusable UI components (Buttons, Loaders)
            │   ├── theme/    # Color, Type, and Theme specifications (Material 3)
            │   └── features/ # Standalone recipe screens (e.g., login, dashboard, listing)
            └── di/           # Dependency Injection Modules