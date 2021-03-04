package com.example.coursefinder.shared.login

data class Model(val primaryButtonText: String, val secondaryButtonText: String)

fun generateModel(): Model {
    return Model("Login", "Continue")
}

