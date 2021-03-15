package com.example.coursefinder.shared.user

const val USER_EMAIL_FIELD = "userEmail"
const val USER_ANON_FIELD = "isUserAnonymous"

data class UserModel(val email: String?, val isAnonymous: Boolean) {
    constructor() : this(null, false)
}
