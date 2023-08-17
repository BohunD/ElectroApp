package com.example.electroapp.data.models

data class User(
    val email: String? = "",
    val username: String? = "",
    val imageUrl: String? = "",
    val followUsers: ArrayList<String>? = arrayListOf()
)
