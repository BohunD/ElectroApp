package com.example.electroapp.presenation.listeners

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.DATA_ADS
import com.example.electroapp.data.util.DATA_AD_LIKES
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.DATA_USER_FOLLOW
import com.example.electroapp.data.util.DATA_USER_LIKES
import com.example.electroapp.presenation.activities.AdvertisementActivity
import com.example.electroapp.presenation.adapters.AdvertisementsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdListenerImpl(
    val adsList: RecyclerView,
    val fragment: Fragment,
    val adapter: AdvertisementsAdapter
) : AdListener {
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var isProcessingLike = false

    override fun onLayoutClick(ad: Advertisement?) {
        ad?.let {
            fragment.requireActivity()
                .startActivity(AdvertisementActivity.newIntent(fragment.requireContext(), it))
        }
    }

    override fun onLikeClick(ad: Advertisement?) {
        if (isProcessingLike) {
            return  // Если обработка запроса в процессе, не позволяем повторно нажимать
        }

        isProcessingLike = true

        var likedUsers = ad?.likedUsers
        val isCurrentlyLiked = ad?.likedUsers?.contains(userId) == true

        firebaseDB.collection(DATA_ADS).document(ad?.adId!!).get()
            .addOnSuccessListener {
                if (isCurrentlyLiked) {
                    likedUsers?.remove(userId)
                } else {
                    likedUsers?.add(userId!!)
                }

                // Обновление лайка в базе данных
                firebaseDB.collection(DATA_ADS).document(ad.adId).update(
                    DATA_AD_LIKES, likedUsers
                ).addOnSuccessListener {
                    // Обновление иконки "лайка" через listener
                    this?.onLikeUpdated(ad, !isCurrentlyLiked)
                    isProcessingLike = false
                }.addOnFailureListener {
                    it.printStackTrace()
                    isProcessingLike = false
                }
            }.addOnFailureListener {
                it.printStackTrace()
                isProcessingLike = false
            }
    }


    override fun onLikeUpdated(ad: Advertisement, isLiked: Boolean) {
        adapter.updateLikeStatus(ad, isLiked)
    }
}