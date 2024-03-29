package com.example.coursefinder.shared.course

import com.example.coursefinder.shared.model.NotificationRow
import com.example.coursefinder.shared.model.WebadvisorCourse
import com.example.coursefinder.shared.networking.WebadvisorApi
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

interface SearchCourseDelegate {
    fun showCourseDetails(courseCode: String, notificationRows: List<NotificationRow>)
}

sealed class RetrievalType {
    object AvailableCourses : RetrievalType()
    class Subscriptions(val email: String) : RetrievalType()
}

class RetrieveCoursesViewModel(
    private val delegate: SearchCourseDelegate?,
    var retrievalType: RetrievalType,
): ViewModel() {
    private val _courses = MutableLiveData(listOf(
        WebadvisorCourse(courseName = "Loading Courses", courseCode = "Loading Courses")
    ))
    val courses = _courses.readOnly()

    var selectedNotificationRowsByCourseCode = mutableMapOf<String, List<NotificationRow>>()

    init {
        refresh()
    }

    fun refresh(retrieveCourses: RetrievalType = retrievalType) {

        // asynchronously get courses from server
        viewModelScope.launch {
            try {
                when (retrieveCourses) {
                    is RetrievalType.AvailableCourses -> _courses.value = WebadvisorApi.getDiscoveryCourses()
                    is RetrievalType.Subscriptions -> _courses.value = WebadvisorApi.getSavedCourses(retrieveCourses.email).map {

                        // store map of saved notifications by course code
                        selectedNotificationRowsByCourseCode[it.toWebadvisorCourse().courseCode] = it.toNotificationRows()

                        // map server response to webadvisor course
                        it.toWebadvisorCourse()
                    }
                }
            } catch (err: Throwable) {
                println("Error getting courses $err")
            }
        }
    }

    fun rowAction(index: Int) {
        delegate?.showCourseDetails(
            _courses.value[index].courseCode,
            selectedNotificationRowsByCourseCode[_courses.value[index].courseCode] ?: listOf()
        )
    }
}