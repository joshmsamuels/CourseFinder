package com.example.coursefinder.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationPreferences(
    val available: Boolean = false,
    val capacity: Boolean = false,
    val courseCode: Boolean = false,
    val courseName: Boolean = false,
    val examTime: Boolean = false,
    val labTime: Boolean = false,
    val lectureTime: Boolean = false,
    val professor: Boolean = false,
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
                capacity = findFieldByNameCaseInsensitive("capacity", notificationRows),
                courseCode = findFieldByNameCaseInsensitive("courseCode", notificationRows),
                courseName = findFieldByNameCaseInsensitive("courseName", notificationRows),
                examTime = findFieldByNameCaseInsensitive("examTime", notificationRows),
                labTime = findFieldByNameCaseInsensitive("labTime", notificationRows),
                lectureTime = findFieldByNameCaseInsensitive("lectureTime", notificationRows),
                professor = findFieldByNameCaseInsensitive("professor", notificationRows),
            )
        }

        private fun findFieldByNameCaseInsensitive(fieldName: String, notificationRows: List<NotificationRow>): Boolean {
            return notificationRows.find {
                it.notificationName.equals(fieldName, ignoreCase = true)
            }?.checked ?: false
        }
    }
}