package com.example.coursefinder.shared.signIn

actual class AuthProvider

actual fun getAuthProviders(): List<AuthProvider> {
    return listOf(AuthProvider())
}