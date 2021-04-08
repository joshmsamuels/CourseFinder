package com.example.coursefinder.shared.networking

import com.example.coursefinder.shared.model.NotificationPreferences
import kotlinx.serialization.Serializable

@Serializable
class SaveNotificationRequest(
    val courseId: String,
    val email: String,
    val term: String,
    val notifications: NotificationPreferences
)