package com.example.coursefinder.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val available: Int = 0,
    val capacity: Int = 0,
    val courseCode: String = "",
    val courseName: String = "",
    val description: String = "",
    val examTime: String = "",
    val labTime: String = "",
    val lectureTime: String = "",
    val professor: String = ""
)
