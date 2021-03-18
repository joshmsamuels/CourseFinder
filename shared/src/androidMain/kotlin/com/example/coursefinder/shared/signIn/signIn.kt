package com.example.coursefinder.shared.signIn

import com.firebase.ui.auth.AuthUI

actual typealias AuthProvider = AuthUI.IdpConfig

actual fun getAuthProviders(): List<AuthProvider> {
    return arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
        AuthUI.IdpConfig.AnonymousBuilder().build()
    )
}