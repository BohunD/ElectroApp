package com.example.electroapp.data.models

import android.net.Uri

data class Advertisement(
    val photos: MutableList<String>?,
    val name: String?,
    val price: String?,
    val categoryName: String?,
    val filters: Map<String, String>?,
    val description: String?,
    val city: String?,
    val dateTime: Long?,
    val userId: String?
){
    constructor() : this(null, null, null, null, null, null, null, null, null)
}
