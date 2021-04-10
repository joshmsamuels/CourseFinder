package com.example.coursefinder.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationPreferences(
    @SerialName("Available")
    val available: Boolean = false,
    @SerialName("Exam")
    val examTime: Boolean = false,
    @SerialName("Lab")
    val labTime: Boolean = false,
    @SerialName("Lecture")
    val lectureTime: Boolean = false,
    @SerialName("Faculty")
    val professor: Boolean = false,
    @SerialName("Seminar")
    val seminar: Boolean = false,
    @SerialName("Status")
    val status: Boolean = false,
)

class NotificationPreferencesFactory {
    companion object {
        // Builds a notification preferences object from the notification rows displayed on the clients
        // Currently the row titles must match the hard-coded strings in this method.
        // TODO: Is there a more robust solution?
        fun makeNotificationPreferencesFromNotificationRow(
            notificationRows: List<NotificationRow>
        ): NotificationPreferences
        {
            return NotificationPreferences(
                available = findFieldByNameCaseInsensitive("available", notificationRows),
                examTime = findFieldByNameCaseInsensitive("examTime", notificationRows),
                labTime = findFieldByNameCaseInsensitive("labTime", notificationRows),
                lectureTime = findFieldByNameCaseInsensitive("lectureTime", notificationRows),
                professor = findFieldByNameCaseInsensitive("professor", notificationRows),
                seminar = findFieldByNameCaseInsensitive("seminar", notificationRows),
                status = findFieldByNameCaseInsensitive("status", notificationRows),
            )
        }

        private fun findFieldByNameCaseInsensitive(fieldName: String, notificationRows: List<NotificationRow>): Boolean {
            return notificationRows.find {
                it.notificationName.equals(fieldName, ignoreCase = true)
            }?.checked ?: false
        }
    }
}