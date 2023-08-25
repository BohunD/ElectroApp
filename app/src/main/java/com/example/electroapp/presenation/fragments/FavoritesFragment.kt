package com.example.electroapp.presenation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.DATA_ADS
import com.example.electroapp.data.util.DATA_AD_ID
import com.example.electroapp.data.util.DATA_AD_LIKES
import com.example.electroapp.data.util.DATA_AD_USER_ID
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.DATA_USER_LIKES
import com.example.electroapp.databinding.FragmentFavoritesBinding
import com.example.electroapp.databinding.FragmentFollowedUsersBinding
import com.example.electroapp.presenation.adapters.AdvertisementsAdapter
import com.example.electroapp.presenation.listeners.AdListenerImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesFragment : ElectroFragment() {


    private lateinit var binding: FragmentFavoritesBinding

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var user: User?=null
    private var adapter: AdvertisementsAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AdvertisementsAdapter(arrayListOf(), userId!!)
        listener = AdListenerImpl(binding.rvAds,this, adapter!!)
        adapter?.setListener(listener!!)
        binding.rvAds.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            updateList()
        }

    }

    override fun onResume() {
        super.onResume()
        updateList()
    }



    private fun updateAdapter(ads: List<Advertisement>) {
        if(ads.isEmpty())
            adapter?.updateAds(arrayListOf())
        else {
            val sortedTweets = ads.sortedWith(compareByDescending { it.dateTime })
            adapter?.updateAds(sortedTweets)
        }
    }

    private fun updateList(){
            val ads = arrayListOf<Advertisement>()
            firebaseDB.collection(DATA_ADS).whereArrayContains(DATA_AD_LIKES, userId!!).get()
                .addOnSuccessListener {list->
                    Log.d("Fav1", list.toString())
                    for(doc in list.documents){
                        val ad = doc.toObject(Advertisement::class.java)

                        ad?.let { ads.add(it) }
                    }
                    Log.d("Fav", ads.toString())
                    updateAdapter(ads)
                }.addOnFailureListener { it.printStackTrace() }



    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FavoritesFragment()
    }
}