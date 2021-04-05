package com.example.coursefinder.shared.course

import dev.icerock.moko.mvvm.viewmodel.ViewModel

enum class SearchCourseBy {
    COURSE_CODE,
    COURSE_NAME
}

data class SearchCourseRow(
    val courseIdentifier: String,
    val selectRowAction: () -> Unit
)

data class SearchCourseModel(
    val rows: List<SearchCourseRow>
)

interface SearchCourseDelegate {
    fun showCourseDetails(courseCode: String)
}

class SearchCourseViewModel(private val delegate: SearchCourseDelegate?): ViewModel() {
    data class Course(val courseCode: String, val courseName: String)
    val courses = listOf(
        Course("CIS*1000", "Intro to Programming"),
        Course("CIS*1500", "Programming"),
        Course("CIS*4250", "Soft design"),
        Course("ACCT*1220", "Intro to accounting"),
        Course("ACCT*4420", "accounting"),
    )

    fun rowAction(index: Int) {
        delegate?.showCourseDetails(courses[index].courseCode)
    }
}