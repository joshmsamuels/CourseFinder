package com.example.coursefinder.shared.networking

import com.example.coursefinder.shared.model.NotificationPreferences
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SaveNotificationRequest(
    @SerialName("CourseId")
    val courseId: String,
    @SerialName("Email")
    val email: String,
    @SerialName("Notifications")
    val notifications: Map<String, NotificationPreferences>
)