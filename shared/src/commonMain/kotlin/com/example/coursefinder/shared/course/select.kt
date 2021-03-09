package com.example.coursefinder.shared.course

import dev.icerock.moko.mvvm.viewmodel.ViewModel

data class SelectModel(
    val selectSearchMethodText: String,
    val searchByCourseCodeButtonText: String,
    val searchByCourseNameButtonText: String,

    val searchByCourseCodeButtonAction: () -> Unit,
    val searchByCourseNameButtonAction: () -> Unit
)

interface SelectDelegate {
    fun searchByCourseCodeButtonAction()
    fun searchByCourseNameButtonAction()
}

class SelectViewModel(private val delegate: SelectDelegate?): ViewModel() {
    fun generateModel(): SelectModel {
        return SelectModel(
            "Select a search method:",
            "Search by Course Code",
            "Search by Course Name",
            { delegate?.searchByCourseCodeButtonAction() },
            { delegate?.searchByCourseNameButtonAction() },
        )
    }
}