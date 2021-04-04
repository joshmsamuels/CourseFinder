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
    fun selectRowAction(courseIdentifier: String)
}

class SearchCourseViewModel(private val delegate: SearchCourseDelegate?): ViewModel() {
    fun generateModel(searchType: SearchCourseBy):  SearchCourseModel {
        data class Course(val courseCode: String, val courseName: String)

        val courses = listOf(
            Course("CIS*1000", "Intro to Programming"),
            Course("CIS*1500", "Programming"),
            Course("CIS*4250", "Soft design"),
            Course("ACCT*1220", "Intro to accounting"),
            Course("ACCT*4420", "accounting"),
        )

        return SearchCourseModel(
            courses.map {
                val courseIdentifier = when (searchType) {
                    SearchCourseBy.COURSE_CODE -> it.courseCode
                    SearchCourseBy.COURSE_NAME -> it.courseName
                }

                SearchCourseRow(
                    courseIdentifier
                ) { delegate?.selectRowAction(courseIdentifier) }
            }
        )
    }

//        return SearchCourseModel(
//            listOf(
//                SearchCourseRow(
//                    "CIS*1000"
//                ) { delegate?.selectRowAction("CIS*1000") },
//                SearchCourseRow(
//                    "CIS*1200"
//                ) { delegate?.selectRowAction("CIS*1200") },
//                SearchCourseRow(
//                    "CIS*1500"
//                ) { delegate?.selectRowAction("CIS*1500") },
//                SearchCourseRow(
//                    "CIS*4030"
//                ) { delegate?.selectRowAction("CIS*4030") },
//                SearchCourseRow(
//                    "CIS*4250"
//                ) { delegate?.selectRowAction("CIS*4250") },
//                SearchCourseRow(
//                    "ACCT*1220"
//                ) { delegate?.selectRowAction("ACCT*1220") },
//                SearchCourseRow(
//                    "ACCT*4220"
//                ) { delegate?.selectRowAction("ACCT*4220") },
//            )
//        )
//    }
}