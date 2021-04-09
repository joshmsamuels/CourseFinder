package com.example.coursefinder.shared.course

import com.example.coursefinder.shared.model.NotificationPreferencesFactory
import com.example.coursefinder.shared.model.NotificationRow
import com.example.coursefinder.shared.networking.WebadvisorApi
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

interface SubscriptionDelegate {
    fun navigateHome()
    fun showError(msg: String)
}

class SubscriptionViewModel(
    private val delegate: SubscriptionDelegate?,
    var courseId: String? = null
): ViewModel() {
    private val _title = MutableLiveData("TITLE")
    private val _emailFieldPrompt = MutableLiveData("Email:")
    private val _saveButtonText = MutableLiveData("Save")
    private val _cancelButtonText = MutableLiveData("Cancel")

    val title = _title.readOnly()
    val notificationRows: MutableLiveData<List<NotificationRow>> = MutableLiveData(listOf())

    val emailFieldPrompt = _emailFieldPrompt.readOnly()
    val emailFieldValue = MutableLiveData("")
    val saveButtonText = _saveButtonText.readOnly()
    val cancelButtonText = _cancelButtonText.readOnly()

    fun refresh(courseId: String? = this.courseId) {
        this.courseId = courseId

        viewModelScope.launch {
            try {
                val cId = courseId.takeUnless {
                    it == null
                } ?: throw Error("Course ID must not be null")

                val course = WebadvisorApi.getCourseByID(cId)

                notificationRows.value = listOf(
                    NotificationRow("available", course.available.toString()),
                    NotificationRow("courseCode", course.courseCode),
                    NotificationRow("courseName", course.courseName),
                    NotificationRow("examTime", course.examTime),
                    NotificationRow("labTime", course.labTime),
                    NotificationRow("lectureTime", course.lectureTime),
                    NotificationRow("professor", course.professor),
                    NotificationRow("seminar", course.seminar),
                )

                _title.value = cId
            } catch (err: Throwable) {
                println("error getting course $courseId - $err")
                delegate?.showError(err.message ?: "Could not get course $courseId")
            }
        }
    }

    fun saveNotifications(
        courseId: String? = this.courseId,
        email: String = emailFieldValue.value,
        term: String
    ) {
        viewModelScope.launch {
            try {
                val cId = courseId.takeUnless {
                    it == null
                } ?: throw Error("Course ID must not be null")

                WebadvisorApi.saveNotificationPreferences(
                    courseId = cId,
                    email = email,
                    notifications = NotificationPreferencesFactory.makeNotificationPreferencesFromNotificationRow(notificationRows.value),
                    term = term
                )

                delegate?.navigateHome()
            } catch (err: Throwable) {
                println("error saving notification - $err")
                delegate?.showError(err.message ?: "Could not save notifications")
            }
        }
    }

    fun cancel() {
        delegate?.navigateHome()
    }
}