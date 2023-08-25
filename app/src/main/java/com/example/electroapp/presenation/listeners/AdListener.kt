package com.example.electroapp.presenation.listeners

import com.example.electroapp.data.models.Advertisement

interface AdListener {
    fun onLayoutClick(ad: Advertisement?)
    fun onLikeClick(ad: Advertisement?)
    fun onLikeUpdated(ad: Advertisement, isLiked: Boolean)
}