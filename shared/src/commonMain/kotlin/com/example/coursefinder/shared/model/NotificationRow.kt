package com.example.coursefinder.shared.model

import com.example.coursefinder.shared.parcel.Parcelable
import com.example.coursefinder.shared.parcel.Parcelize

@Parcelize
data class NotificationRow(
    var notificationName: String,
    var courseRowDetail: String,
    var checked: Boolean = false
): Parcelable