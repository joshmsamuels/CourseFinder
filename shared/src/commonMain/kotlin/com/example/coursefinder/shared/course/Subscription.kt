package com.example.coursefinder.shared.course

import dev.icerock.moko.mvvm.viewmodel.ViewModel

data class Row(val title: String, val subtitle: String)

data class SubscriptionModel(
    val title: String,
    val contentRows: List<Row>,
    val textFieldPrompt: String,
    val primaryButtonText: String,
    val secondaryButtonText: String,

    val saveAction: () -> Unit,
    val cancelAction: () -> Unit
)

interface SubscriptionDelegate {
    fun saveAction()
    fun cancelAction()
}

class SubscriptionViewModel(private val delegate: SubscriptionDelegate?): ViewModel() {
    fun generateModel(courseCode: String): SubscriptionModel {
        return SubscriptionModel(
            "Course: $courseCode",
            listOf(
                Row("Test Title", "Test Subtitle"),
                Row("Test Title", "Test Subtitle"),
                Row("Test Title", "Test Subtitle"),
                Row("Test Title", "Test Subtitle"),
                Row("Test Title", "Test Subtitle"),
            ),
            "Email:",
            "Save",
            "Cancel",
            { delegate?.saveAction() },
            { delegate?.cancelAction() }
        )
    }
}