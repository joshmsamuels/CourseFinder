package com.example.coursefinder.shared.networking.coursesEndpoint

import com.example.coursefinder.shared.model.WebadvisorCourse
import kotlinx.serialization.Serializable

@Serializable
data class CourseDiscoveryResponse(
    val CourseId: String = "",
    val SectionNameTitle: String = "",
) {
    fun toWebadvisorCourse(): WebadvisorCourse {
        return WebadvisorCourse(
            courseCode = this.CourseId,
            courseName = this.SectionNameTitle,
        )
    }
}
