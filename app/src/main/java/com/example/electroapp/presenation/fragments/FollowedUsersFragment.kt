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
import com.example.electroapp.data.util.DATA_AD_USER_ID
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.databinding.FragmentFollowedUsersBinding
import com.example.electroapp.presenation.adapters.AdvertisementsAdapter
import com.example.electroapp.presenation.listeners.AdListenerImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FollowedUsersFragment : ElectroFragment() {

    private lateinit var binding: FragmentFollowedUsersBinding

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
        binding = FragmentFollowedUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUser()
        adapter = AdvertisementsAdapter(arrayListOf(),userId!!)
        listener = AdListenerImpl(binding.rvAds,this, adapter!!)
        adapter?.setListener(listener!!)
        binding.rvAds.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            initUser()
        }

    }

    override fun onResume() {
        super.onResume()
        initUser()
    }

    private fun initUser() {
        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener { it ->
                user = it.toObject(User::class.java)
                Log.d("FOLLOWED", user.toString())
                updateList() // Вызываем updateList() после получения данных пользователя
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun updateAdapter(ads: List<Advertisement>) {
        val sortedTweets = ads.sortedWith(compareByDescending { it.dateTime })
        adapter?.updateAds(sortedTweets)
    }

    private fun updateList(){
        Log.d("FOLLOWED2", user.toString())
        user?.let {
            val ads = arrayListOf<Advertisement>()
            for(seller in it.followUsers!!){
                firebaseDB.collection(DATA_ADS).whereEqualTo(DATA_AD_USER_ID, seller).get()
                    .addOnSuccessListener { snapshot->
                        for(document in snapshot.documents){
                            Log.d("FOLLOWED3", document.toObject(Advertisement::class.java).toString())
                            val ad = document.toObject(Advertisement::class.java)
                            ad?.let {
                                ads.add(ad)
                            }
                            updateAdapter(ads)
                        }
                    }.addOnFailureListener {e->
                        Log.d("FOLLOWED3", e.toString())

                        e.printStackTrace()

                    }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FollowedUsersFragment()
    }
}