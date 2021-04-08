package com.example.coursefinder.shared.networking

import com.example.coursefinder.shared.model.Course
import com.example.coursefinder.shared.model.NotificationPreferences
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object WebadvisorApi {
    // TODO: Update URL for real API once it is completed
    private const val baseUrl = "https://api.mocki.io/v1/03581331"
    private const val emailBaseUrl = "https://64zwv.mocklab.io"

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllCourses(): List<Course> {
        return httpClient.get(baseUrl)
    }

    suspend fun getSavedCourses(email: String): List<Course> {
        return httpClient.get("${emailBaseUrl}?email=$email")
    }

    suspend fun saveNotificationPreferences(
        courseId: String,
        email: String,
        notifications: NotificationPreferences,
        term: String
    ) {
        return httpClient.post {
            url("${baseUrl}/api/v1/notifications")
            contentType(ContentType.Application.Json)
            body = SaveNotificationRequest(
                courseId = courseId,
                email = email,
                notifications = notifications,
                term = term
            )
        }
    }

    // TODO: close client
}
