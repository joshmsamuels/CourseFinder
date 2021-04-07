package com.example.coursefinder.shared.course

import com.example.coursefinder.shared.model.NotificationRow
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

interface SubscriptionDelegate {
    fun navigateHome()
    fun showError(msg: String)
}

class SubscriptionViewModel(private val delegate: SubscriptionDelegate?): ViewModel() {
    private val _title = MutableLiveData("TITLE")
    private val _textFieldPrompt = MutableLiveData("Email:")
    private val _saveButtonText = MutableLiveData("Save")
    private val _cancelButtonText = MutableLiveData("Cancel")

    val title = _title.readOnly()
    val notificationRows: MutableLiveData<List<NotificationRow>> = MutableLiveData(
        listOf(
            NotificationRow("Test Title", "Test Subtitle"),
            NotificationRow("Test Title", "Test Subtitle"),
            NotificationRow("Test Title", "Test Subtitle"),
            NotificationRow("Test Title", "Test Subtitle"),
            NotificationRow("Test Title", "Test Subtitle"),
        )
    )

    val textFieldPrompt = _textFieldPrompt.readOnly()
    val textField = MutableLiveData("")
    val saveButtonText = _saveButtonText.readOnly()
    val cancelButtonText = _cancelButtonText.readOnly()

    fun refresh(courseCode: String? = null) {}

    fun saveNotifications() {
        viewModelScope.launch {
            try {
                // TODO: Save on server
                delegate?.navigateHome()

            } catch (err: Throwable) {
                delegate?.showError(err.message ?: "Could not save notifications")
            }
        }
    }

    fun cancel() {
        delegate?.navigateHome()
    }
}