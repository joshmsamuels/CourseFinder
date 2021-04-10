package com.example.coursefinder.shared.networking.coursesEndpoint

import com.example.coursefinder.shared.model.WebadvisorCourse
import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val Available: Int = 0,
    val CourseId: String = "",
    val Exam: String = "",
    val Faculty: String = "",
    val Lab: String = "",
    val Lecture: String = "",
    val SectionNameTitle: String = "",
    val Seminar: String = "",
    val Status: String = "",
) {
     fun toWebadvisorCourse(): WebadvisorCourse {
         return WebadvisorCourse(
             available = this.Available,
             courseCode = this.CourseId,
             courseName = this.SectionNameTitle,
             examTime = this.Exam,
             labTime = this.Lab,
             lectureTime = this.Lecture,
             professor = this.Faculty,
             seminar = this.Seminar,
             status = this.Status
         )
     }
}