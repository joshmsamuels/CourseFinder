package com.example.coursefinder.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class WebadvisorCourse(
    val available: Int = 0,
    val capacity: Int = 0,
    var courseCode: String = "",
    var courseName: String = "",
    var examTime: String = "",
    var labTime: String = "",
    var lectureTime: String = "",
    var professor: String = "",
    var seminar: String = "",
    var status: String = ""
) {
    // TODO: Move to constructor or notification row class?
    init {
        this.courseCode = naIfBlank(this.courseCode)

        if (this.courseName.isBlank()) {
            this.courseName = "N/A"
        } else if (this.courseName.contains("Loading")) {
            // Do nothing -- We do not need to massage loading courses
        } else {
            val section = this.courseName.substringBefore("(", "")
                .substringAfterLast('*')
            // Replaces html encoded ampersands with an ampersand and removes the course code
            // and adds back the section to distinguish sections of the same course
            this.courseName = this.courseName
                .substringAfter(")", "")
                .replace("&amp;", "&") + ": $section"
        }

        this.examTime = naIfBlank(this.examTime)
        this.labTime = naIfBlank(this.labTime)
        this.lectureTime = naIfBlank(this.lectureTime)
        this.professor = naIfBlank(this.professor)
        this.seminar = naIfBlank(this.seminar)
        this.status = naIfBlank(this.status)
    }

    fun formatForPrinting(): WebadvisorCourse {
        return WebadvisorCourse(
            available = this.available,
            capacity = this.capacity,
            courseCode = this.courseCode,
            courseName = this.courseName,
            examTime = " ${this.examTime.substringBefore("-", "")} ${this.examTime.substringAfter("EXAM", "")}",
            labTime = " ${this.labTime.substringBefore("-", "")} ${this.labTime.substringAfter("LAB", "")}",
            lectureTime = " ${this.lectureTime.substringBefore("-", "")} ${this.lectureTime.substringAfter("LEC", "")}",
            professor = this.professor,
            seminar = " ${this.seminar.substringBefore("-", "")} ${this.seminar.substringAfter("SEM", "")}",
            status = this.status
        )
    }

    private fun naIfBlank(str: String): String {
        if (str.isBlank()) {
            return "N/A"
        }

        return str
    }
}
