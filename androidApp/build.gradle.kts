plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.activity:activity-ktx:1.2.2")
    implementation("androidx.fragment:fragment-ktx:1.3.2")
    implementation("dev.icerock.moko:mvvm-livedata:0.9.1")
    implementation("com.firebaseui:firebase-ui-auth:6.4.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    implementation("dev.icerock.moko:mvvm-core:0.9.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.example.coursefinder.androidApp"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

apply(mapOf("plugin" to "com.google.gms.google-services"))
apply(mapOf("plugin" to "androidx.navigation.safeargs.kotlin"))
