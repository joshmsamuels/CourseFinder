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
            NotificationRow("available", course.available.toString(), preferences.available),
            NotificationRow("examTime", course.examTime, preferences.examTime),
            NotificationRow("labTime", course.labTime, preferences.labTime),
            NotificationRow("lectureTime", course.lectureTime, preferences.lectureTime),
            NotificationRow("professor", course.professor, preferences.professor),
            NotificationRow("seminar", course.seminar, preferences.seminar),
            NotificationRow("status", course.status, preferences.status),
        )
    }
}