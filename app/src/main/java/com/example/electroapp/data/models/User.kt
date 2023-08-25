package com.example.electroapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val email: String? = "",
    val username: String? = "",
    val phoneNumber: String? = "",
    val imageUrl: String? = "",
    val followUsers: ArrayList<String>? = arrayListOf(),
    val likedAds: ArrayList<String>?= arrayListOf()
): Parcelable
