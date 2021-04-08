package com.example.coursefinder.shared.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationPreferences(
    @SerialName("Available")
    val available: Boolean = false,
    @SerialName("Capacity")
    val capacity: Boolean = false,
    @SerialName("CourseCode")
    val courseCode: Boolean = false,
    @SerialName("CourseName")
    val courseName: Boolean = false,
    @SerialName("ExamTime")
    val examTime: Boolean = false,
    @SerialName("LabTime")
    val labTime: Boolean = false,
    @SerialName("LectureTime")
    val lectureTime: Boolean = false,
    @SerialName("Professor")
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