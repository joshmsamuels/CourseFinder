package com.example.coursefinder.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class WebadvisorCourse(
    val available: Int = 0,
    val capacity: Int = 0,
    val courseCode: String = "",
    val courseName: String = "",
    val examTime: String = "",
    val labTime: String = "",
    val lectureTime: String = "",
    val professor: String = "",
    val seminar: String = "",
    val status: String = ""
) {
    fun formatForPrinting(): WebadvisorCourse {
//        this.examTime = this.examTime.substringAfter("EXAM")

        return this
    }
}
