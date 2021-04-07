package com.example.coursefinder.shared.networking

import com.example.coursefinder.shared.model.Course
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

object WebadvisorApi {
    // TODO: Update URL for real API once it is completed
    private const val baseUrl = "https://api.mocki.io/v1/03581331"

    private val httpClient = HttpClient() {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getCourses(): List<Course> {
        return httpClient.get(baseUrl)
    }

    // TODO: close client
}
