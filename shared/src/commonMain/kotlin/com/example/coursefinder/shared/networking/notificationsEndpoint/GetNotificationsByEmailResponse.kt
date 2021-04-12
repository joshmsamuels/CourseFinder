package com.example.coursefinder.shared.networking.notificationsEndpoint

import com.example.coursefinder.shared.model.NotificationPreferences
import com.example.coursefinder.shared.model.NotificationRow
import com.example.coursefinder.shared.model.WebadvisorCourse
import com.example.coursefinder.shared.networking.WebadvisorApi
import kotlinx.serialization.Serializable

@Serializable
data class GetNotificationsByEmailResponse(
    val CourseId: String,
    val Email: String,
    val Notifications: Map<String, NotificationPreferences>
) {
    fun toWebadvisorCourse(): WebadvisorCourse {
        return WebadvisorCourse(
            courseCode = this.CourseId,
        )
    }

    suspend fun toNotificationRows(): List<NotificationRow> {
        // Due to a data limitation Winter 2021 is currently the only supported semester
        val preferences = this.Notifications["Winter 2021"].takeUnless {
            it == null
        } ?: return listOf()

        val course = WebadvisorApi.getCourseByID(CourseId)

        return listOf(
            NotificationRow("Available Spots", course.available.toString(), preferences.available),
            NotificationRow("Exam Time", course.examTime, preferences.examTime),
            NotificationRow("Lab Time", course.labTime, preferences.labTime),
            NotificationRow("Lecture Time", course.lectureTime, preferences.lectureTime),
            NotificationRow("Professor", course.professor, preferences.professor),
            NotificationRow("Seminar", course.seminar, preferences.seminar),
            NotificationRow("Status", course.status, preferences.status),
        )
    }
}