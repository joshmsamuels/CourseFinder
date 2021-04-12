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
    fun showToast(msg: String)
}

class SubscriptionViewModel(
    private val delegate: SubscriptionDelegate?,
    var courseId: String? = null,
    val notificationRows: MutableLiveData<List<NotificationRow>> = MutableLiveData(listOf(
        NotificationRow("Available Spots", "Loading..."),
        NotificationRow("Exam Time", "Loading..."),
        NotificationRow("Lab Time", "Loading..."),
        NotificationRow("Lecture Time", "Loading..."),
        NotificationRow("Professor", "Loading..."),
        NotificationRow("Seminar", "Loading..."),
        NotificationRow("Status", "Loading..."),
    )),
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

                val course = WebadvisorApi.getCourseByID(cId).formatForPrinting()

                notificationRows.value = notificationRows.value.map {
                    when(it.notificationName) {
                        "Available Spots" -> {
                            it.courseRowDetail = course.available.toString()
                        }
                        "Exam Time" -> {
                            it.courseRowDetail = course.examTime
                        }
                        "Lab Time" -> {
                            it.courseRowDetail = course.labTime
                        }
                        "Lecture Time" -> {
                            it.courseRowDetail = course.lectureTime
                        }
                        "Professor" -> {
                            it.courseRowDetail = course.professor
                        }
                        "Seminar" -> {
                            it.courseRowDetail = course.seminar
                        }
                        "Status" -> {
                            it.courseRowDetail = course.status
                        }
                    }

                    it
                }

                _title.value = cId
            } catch (err: Throwable) {
                println("error getting course $courseId - $err")
                delegate?.showToast(err.message ?: "Could not get course $courseId")
            }
        }
    }

    fun saveNotifications(
        courseId: String? = this.courseId,
        term: String
    ) {

        viewModelScope.launch {
            try {
                val cId = courseId.takeUnless {
                    it == null
                } ?: throw Error("Course ID must not be null")

                if (notificationRows.value.none { it.checked }) {
                    throw Error("Please select at least one notification to save")
                }

                val email: String = emailFieldValue.value.takeUnless {
                    it.isEmpty()
                } ?: throw Error("Please enter your email address or login")

                WebadvisorApi.saveNotificationPreferences(
                    courseId = cId,
                    email = email,
                    notifications = NotificationPreferencesFactory.makeNotificationPreferencesFromNotificationRow(notificationRows.value),
                    term = term
                )

                delegate?.showToast("Saved notifications for $courseId")

                delegate?.navigateHome()
            } catch (err: Throwable) {
                println("error saving notification - $err")
                delegate?.showToast(err.message ?: "Could not save notifications")
            }
        }
    }

    fun cancel() {
        delegate?.navigateHome()
    }
}