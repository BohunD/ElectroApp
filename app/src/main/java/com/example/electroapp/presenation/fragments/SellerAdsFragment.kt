package com.example.electroapp.presenation.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.electroapp.R
import com.example.electroapp.data.models.Advertisement
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.DATA_ADS
import com.example.electroapp.data.util.DATA_AD_USER_ID
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.DATA_USER_FOLLOW
import com.example.electroapp.data.util.loadUrl
import com.example.electroapp.databinding.FragmentPhotoVpFitCenterBinding
import com.example.electroapp.databinding.FragmentSellerAdsBinding
import com.example.electroapp.presenation.adapters.AdvertisementsAdapter
import com.example.electroapp.presenation.listeners.AdListenerImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage


class SellerAdsFragment : ElectroFragment() {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private var adapter: AdvertisementsAdapter?=null
    private var sellerId: String?=null
    private var user: User?=null
    private var seller: User?=null


    private lateinit var binding: FragmentSellerAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSellerAdsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        arguments.let {
            var ad: Advertisement? = null
            listener = AdListenerImpl(binding.rvYourAds,this@SellerAdsFragment)
            adapter = AdvertisementsAdapter(arrayListOf())
            adapter!!.setListener(listener!!)
            binding.profileProgressLayout.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ad = it?.getParcelable(KEY_AD, Advertisement::class.java)
            } else {
                ad = it?.getParcelable<Advertisement>(KEY_AD)
            }
            sellerId = ad?.userId
            firebaseDB.collection(DATA_USERS).document(ad?.userId!!).get()
                .addOnSuccessListener { snapshot ->
                    seller = snapshot.toObject(User::class.java)
                    seller?.let {
                        binding.apply {
                            ivUserPhoto.loadUrl(ivUserPhoto.context, seller?.imageUrl)
                            tvName.text = seller?.username
                            tvPhone.text = seller?.phoneNumber
                        }

                    }
                    binding.profileProgressLayout.visibility = View.GONE

                }.addOnFailureListener {
                    it.printStackTrace()
                    binding.profileProgressLayout.visibility = View.GONE
                }

            binding.rvYourAds.adapter = adapter
            if(user?.followUsers?.contains(sellerId) == true)
                binding.btnFollow.text = "Unfollow"
            else binding.btnFollow.text = "Follow"
            binding.btnFollow.setOnClickListener { onFollowClicked() }
            updateList()
        }
    }

    private fun initUser(){
        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener { it ->
                user = it.toObject(User::class.java)
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun onFollowClicked(){
        initUser()
        if(sellerId != userId){
            if(user?.followUsers?.contains(sellerId) == true){
                AlertDialog.Builder(context).setTitle("Unfollow ${seller?.username}?")
                    .setPositiveButton("Yes"){ _, _ ->
                        binding.profileProgressLayout.visibility = View.VISIBLE
                        var followedUsers = user?.followUsers
                        if(followedUsers == null)
                            followedUsers = arrayListOf()
                        followedUsers.remove(sellerId)
                        firebaseDB.collection(DATA_USERS).document(userId!!).update(
                            DATA_USER_FOLLOW, followedUsers
                        ).addOnSuccessListener {
                            Toast.makeText(requireContext(),
                                "Unfollowed", Toast.LENGTH_SHORT).show()
                            binding.profileProgressLayout.visibility = View.GONE
                        }
                            .addOnFailureListener { Toast.makeText(requireContext(),
                                "Error :(", Toast.LENGTH_SHORT).show()
                                binding.profileProgressLayout.visibility = View.GONE
                            }
                    } .setNegativeButton("Cancel") { _, _ -> }.show()
            } else{
                AlertDialog.Builder(context).setTitle("Follow ${seller?.username}?")
                    .setPositiveButton("Yes"){ _, _ ->
                        binding.profileProgressLayout.visibility = View.VISIBLE
                        var followedUsers = user?.followUsers
                        if(followedUsers == null)
                            followedUsers = arrayListOf()
                        followedUsers?.add(sellerId!!)
                        firebaseDB.collection(DATA_USERS).document(userId!!).update(
                            DATA_USER_FOLLOW, followedUsers
                        ).addOnSuccessListener {
                            Toast.makeText(requireContext(),
                                "Unfollowed", Toast.LENGTH_SHORT).show()
                            binding.profileProgressLayout.visibility = View.GONE
                        }
                            .addOnFailureListener { Toast.makeText(requireContext(),
                                "Error :(", Toast.LENGTH_SHORT).show()
                                binding.profileProgressLayout.visibility = View.GONE
                            }
                    } .setNegativeButton("Cancel") { _, _ -> }.show()
            }
        }
    }

    private fun updateList() {
        val ads = arrayListOf<Advertisement>()
        firebaseDB.collection(DATA_ADS).whereEqualTo(DATA_AD_USER_ID, sellerId).get()
            .addOnSuccessListener { list ->
                for (document in list.documents) {
                    val ad = document.toObject(Advertisement::class.java)
                    ad?.let { ads.add(ad)}
                }
                adapter?.updateAds(ads)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong... :(", Toast.LENGTH_SHORT
                ).show()            }
    }

    companion object {

        const val KEY_AD = "advertisement_key"

        @JvmStatic
        fun newInstance(ad: Advertisement) =
            SellerAdsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_AD, ad)
                }
            }
    }
}