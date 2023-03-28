package com.example.project

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class MyDataModel(val title: String, val time: String, val common: String, val date: String) : Parcelable
