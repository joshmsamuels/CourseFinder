package com.example.coursefinder.shared.course

import com.example.coursefinder.shared.model.Course
import com.example.coursefinder.shared.networking.WebadvisorApi
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

interface SearchCourseDelegate {
    fun showCourseDetails(courseCode: String)
}

class SearchCourseViewModel(private val delegate: SearchCourseDelegate?): ViewModel() {

    private val _courses = MutableLiveData<List<Course>>(listOf())

    val courses = _courses.readOnly()

    fun refresh() {
        viewModelScope.launch {
            try {
                _courses.value = WebadvisorApi.getCourses()
            } catch (err: Throwable) {
                TODO("handle error when getting courses")
            }
        }
    }

    fun rowAction(index: Int) {
        delegate?.showCourseDetails(_courses.value[index].courseCode)
    }
}