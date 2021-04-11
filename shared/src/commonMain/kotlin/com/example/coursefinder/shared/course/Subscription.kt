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
    var courseId: String? = null,
    val notificationRows: MutableLiveData<List<NotificationRow>> = MutableLiveData(listOf()),
    email: String = ""
): ViewModel() {
    private val _title = MutableLiveData("TITLE")
    private val _emailFieldPrompt = MutableLiveData("Email:")
    private val _saveButtonText = MutableLiveData("Save")
    private val _cancelButtonText = MutableLiveData("Cancel")

    val title = _title.readOnly()


    val emailFieldPrompt = _emailFieldPrompt.readOnly()
    val emailFieldValue = MutableLiveData(email)
    val saveButtonText = _saveButtonText.readOnly()
    val cancelButtonText = _cancelButtonText.readOnly()

    init {
        refresh()
    }

    fun refresh(courseId: String? = this.courseId) {
        this.courseId = courseId

        viewModelScope.launch {
            try {
                val cId = courseId.takeUnless {
                    it == null
                } ?: throw Error("Course ID must not be null")

                val course = WebadvisorApi.getCourseByID(cId)

                notificationRows.value = listOf(
                    NotificationRow("Available Spots", course.available.toString()),
                    NotificationRow("Exam Time", course.examTime),
                    NotificationRow("Lab Time", course.labTime),
                    NotificationRow("Lecture Time", course.lectureTime),
                    NotificationRow("Professor", course.professor),
                    NotificationRow("Seminar", course.seminar),
                    NotificationRow("Status", course.status),
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