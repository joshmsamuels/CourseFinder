buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("com.google.gms:google-services:4.3.5")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.4")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.32")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://dl.bintray.com/icerockdev/moko")
        maven(url ="https://kotlin.bintray.com/kotlinx")

    }
}