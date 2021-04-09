package com.example.coursefinder.shared.course

import com.example.coursefinder.shared.model.WebadvisorCourse
import com.example.coursefinder.shared.networking.WebadvisorApi
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

interface SearchCourseDelegate {
    fun showCourseDetails(courseCode: String)
}

sealed class RetrievalType {
    object AvailableCourses : RetrievalType()
    class Subscriptions(val email: String) : RetrievalType()
}

class RetrieveCoursesViewModel(
    private val delegate: SearchCourseDelegate?,
    var retrievalType: RetrievalType,
): ViewModel() {
    private val _courses = MutableLiveData<List<WebadvisorCourse>>(listOf())
    val courses = _courses.readOnly()

    init {
        refresh()
    }

    fun refresh(retrieveCourses: RetrievalType = retrievalType) {
        viewModelScope.launch {
            try {
                when (retrieveCourses) {
                    is RetrievalType.AvailableCourses -> _courses.value = WebadvisorApi.getDiscoveryCourses()
                    is RetrievalType.Subscriptions -> _courses.value = WebadvisorApi.getSavedCourses(retrieveCourses.email)
                }
            } catch (err: Throwable) {
                println("Error getting courses $err")
                TODO("handle error when getting courses")
            }
        }
    }

    fun rowAction(index: Int) {
        delegate?.showCourseDetails(_courses.value[index].courseCode)
    }
}