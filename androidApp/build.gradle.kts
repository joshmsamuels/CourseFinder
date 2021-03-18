plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")

    implementation("dev.icerock.moko:mvvm-core:0.9.1")

    // Card View
    implementation ("androidx.cardview:cardview:1.0.0")

    // Recyclerview
    implementation ("androidx.recyclerview:recyclerview:1.0.0")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.example.coursefinder.androidApp"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}