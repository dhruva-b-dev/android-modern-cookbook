# Location Manager (Android Modern Cookbook)

A modern, lifecycle-aware Android module built with **Jetpack Compose** that demonstrates how to clean, request, and fetch a user's geographic coordinates using Google Play Services' `FusedLocationProviderClient`, combined with the native `Geocoder` API to reverse-geocode those coordinates into human-readable physical addresses.

This project serves as a production-ready recipe in the `android-modern-cookbook` ecosystem, implementing robust Runtime Permission handling, clean MVVM/State architecture, and thorough testing coverage (both Unit and Instrumentation tests).

*Inspired by Mun Bonecci's Medium guide: [How to get your location in Jetpack Compose](https://medium.com/@munbonecci/how-to-get-your-location-in-jetpack-compose-f085031df4c1).*

---

## 🚀 Features

- **Jetpack Compose Integration**: Seamless UI permission requests handling using runtime contracts.
- **Fused Location Provider Client**: Utilizes Google Play Services to fetch accurate, fast location metrics (`COARSE` and `FINE` accuracy support).
- **Reverse Geocoding**: Converts latitude and longitude into physical address components (Street, City, State, Country) asynchronously via the `Geocoder` API.
- **Permission Lifecycle Awareness**: Dynamic UI response handling for states where permission is Granted, Denied, or Permanently Revoked.
- **Comprehensive Testing Suite**: Includes deterministic Unit Tests and device/emulator UI Instrumentation Tests.

---

## 🛠 Tech Stack & Dependencies

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Location Services**: `com.google.android.gms:play-services-location`
- **Permissions**: Accompanist Permissions library / AndroidX Activity-Compose contracts.
- **Testing**:
    - [JUnit4](https://junit.org/junit4/) & [MockK](https://mockk.io/) for Unit Testing.
    - [Espresso](https://developer.android.com/training/testing/espresso) & Compose UI Test (`androidx.compose.ui:ui-test-junit4`) for Instrumentation/UI Testing.

---

## 📸 Architectural Overview

The workflow of the Location Module flows seamlessly from UI composition down to the Android Hardware layer:

1. **Permission Request** ➔ Jetpack Compose triggers `rememberLauncherForActivityResult`.
2. **Coordinate Retrieval** ➔ `FusedLocationProviderClient` grabs `lastLocation` or `getCurrentLocation`.
3. **Address Mapping** ➔ `Geocoder` processes the raw data off-main-thread and yields localized address streams.

---

## 📦 Setup & Implementation Details

### 1. Permissions configuration
The `AndroidManifest.xml` enforces the following location access vectors:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />