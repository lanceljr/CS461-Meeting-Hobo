package com.example.project

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class MyDataModel(
    val id: String,
    val title: String, val time: String, val date: String,
    val sentences: ArrayList<Pair<String, String>>, val hasBeenAssigned: Boolean
) : Parcelable
