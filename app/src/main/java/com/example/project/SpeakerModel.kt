package com.example.project

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpeakerModel(val sentences: Pair<String, Int>) :
    Parcelable