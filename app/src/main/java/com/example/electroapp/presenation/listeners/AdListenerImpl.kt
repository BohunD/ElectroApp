package com.example.electroapp.presenation.listeners

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.presenation.activities.AdvertisementActivity
import com.example.electroapp.presenation.viewmodels.AdvertisementViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdListenerImpl(
    val adsList: RecyclerView,
    val fragment: Fragment
): AdListener {
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onLayoutClick(ad: Advertisement?) {
        ad?.let{
            fragment.requireActivity().startActivity(AdvertisementActivity.newIntent(fragment.requireContext(),it))
        }
    }

    override fun onLikeClick(ad: Advertisement?) {
        TODO("Not yet implemented")
    }
}