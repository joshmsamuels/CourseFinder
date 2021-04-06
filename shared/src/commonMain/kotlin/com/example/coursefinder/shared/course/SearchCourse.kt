package com.example.coursefinder.shared.course

import com.example.coursefinder.shared.model.Course
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel

interface SearchCourseDelegate {
    fun showCourseDetails(courseCode: String)
}

class SearchCourseViewModel(private val delegate: SearchCourseDelegate?): ViewModel() {

    private val _courses = listOf(
        MutableLiveData(Course("CIS*1000", "Intro to Programming")),
        MutableLiveData(Course("CIS*1500", "Programming")),
        MutableLiveData(Course("CIS*4250", "Soft design")),
        MutableLiveData(Course("ACCT*1220", "Intro to accounting")),
        MutableLiveData( Course("ACCT*4420", "accounting")),

    )

    val courses = _courses.map { it.readOnly() }

    fun rowAction(index: Int) {
        delegate?.showCourseDetails(_courses[index].value.courseCode)
    }
}