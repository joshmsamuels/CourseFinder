package com.example.coursefinder.shared.networking

import com.example.coursefinder.shared.model.WebadvisorCourse
import com.example.coursefinder.shared.model.NotificationPreferences
import com.example.coursefinder.shared.networking.coursesEndpoint.CourseDiscoveryResponse
import com.example.coursefinder.shared.networking.coursesEndpoint.CourseResponse
import com.example.coursefinder.shared.networking.notificationsEndpoint.GetNotificationsByEmailResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object WebadvisorApi {
    private const val baseUrl = "https://course-notify-server-staging.herokuapp.com/api/v1"
    private const val coursesAPI = "$baseUrl/courses/"
    private const val discoveryCoursesAPI = "$baseUrl/courses/discovery/"
    private const val notificationsAPI = "$baseUrl/notifications/"

    // create http client with serializer and content type
    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
            accept(ContentType.Application.Json)
        }
    }

    // gets discovery courses from server
    suspend fun getDiscoveryCourses(): List<WebadvisorCourse> {
        return httpClient.get<List<CourseDiscoveryResponse>>(discoveryCoursesAPI).map {
            it.toWebadvisorCourse()
        }
    }

    // gets course by id from server
    suspend fun getCourseByID(courseId: String): WebadvisorCourse {
        return httpClient.get<CourseResponse>("$coursesAPI?CourseId=$courseId").toWebadvisorCourse()
    }

    // gets saved courses
    suspend fun getSavedCourses(email: String): List<GetNotificationsByEmailResponse> {
        return httpClient.get("${notificationsAPI}$email/")
    }

    // saves notification preferences by email to server
    suspend fun saveNotificationPreferences(
        courseId: String,
        email: String,
        notifications: NotificationPreferences,
        term: String
    ) {
        return httpClient.post {
            url(notificationsAPI)
            contentType(ContentType.Application.Json)
            body = SaveNotificationRequest(
                courseId = courseId,
                email = email,
                notifications = mapOf(term to notifications),
            )
        }
    }
}
