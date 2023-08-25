package com.example.electroapp.data.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Advertisement(
    val adId: String?,
    val photos: MutableList<String>?,
    val name: String?,
    val price: String?,
    val categoryName: String?,
    val filters: Map<String, String>?,
    val description: String?,
    val city: String?,
    val dateTime: Long?,
    val userId: String?,
    val likedUsers: ArrayList<String>?=null
): Parcelable{
    constructor() : this(null, null, null, null, null, null, null, null, null, null, null)
}
