package com.example.project

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Note(val title: String, val date: String, val notes: String, val noteId: String) : Parcelable
